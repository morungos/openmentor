/* =======================================================================
 * Copyright 2004-2006 The OpenMentor Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================================
 */
/*
 * Created on 21-Dec-2005
 *
 */
package uk.org.openmentor.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.openmentor.report.ReportRenderEngine;
import uk.org.openmentor.report.constituents.ChartConstituent;
import uk.org.openmentor.report.constituents.ReportConstituent;
import uk.org.openmentor.model.DataBook;
import uk.org.openmentor.businesslogic.BusinessLogic;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * Describe class <code>ReportController</code> here. Originally by
 * Hassan, adapted to fit in the Spring framework by Stuart.
 *
 * @author <a href="mailto:Ian.Craw@openability.co.uk">Ian Craw</a>
 * @version 1.0
 */
public class ReportController implements Controller {

    public static final String  ATTRIB_REPORT_VEC   = "ReportVec";
    /*Define query string variables here for more reporting options*/
    private static final String PARAM_REQUEST_TYPE  = "RequestType";
    private static final String PARAM_REPORT_FOR    = "ReportFor";
    private static final String PARAM_ID            = "ID";
    private static final String PARAM_CHART_TYPE    = "ChartType";
    private static final String REQ_CHART_IMAGE     = "ChartImage";
    private static final String REQ_TABLE           = "Table";

        /** Used in SQL code to delimit comments. */
    private static final String SQL_COMMENT_SEPARATOR = "__SEP__";

    /**
     * The ModelAndView to be returned. This is an instance variable
     * because we need to return null if we are handling the view
     * directly, otherwise an object. This allows the decision about
     * what to return to be delegated to methods.
     */
    private ModelAndView mav = null;

    /**
     * Our usual logger.
     */
    private static Log log = LogFactory.getLog(ReportController.class);

    /**
     * Describe variable <code>businessLogic</code> here.
     */
    private BusinessLogic businessLogic = null;

    /**
     * Describe <code>setBusinessLogic</code> method here.
     *
     * @param theBusinessLogic a <code>BusinessLogic</code> value
     */
    public final void setBusinessLogic(final BusinessLogic theBusinessLogic) {
        this.businessLogic = theBusinessLogic;
    }

    /**
     * Describe <code>handleRequest</code> method here.
     *
     * @param request a <code>HttpServletRequest</code> value
     * @param response a <code>HttpServletResponse</code> value
     * @return a <code>ModelAndView</code> value
     * @exception ServletException if an error occurs
     * @exception IOException if an error occurs
     */
    public final ModelAndView handleRequest(final HttpServletRequest request,
                                            final HttpServletResponse response)
        throws ServletException, IOException {

        if (log.isDebugEnabled()) {
            log.debug("Handling ReportController request");
        }

        if (!request.getMethod().equals("GET")) {
            throw new ServletException("ReportController only supports"
                                       + " GET requests");
        }

        String requestType = request.getParameter(PARAM_REQUEST_TYPE);

        if (requestType == null) {
            throw new ServletException("Missing request type");
        }
        if (log.isDebugEnabled()) {
            log.debug("Request for chart type " + requestType);
        }
        try {
                DataBook dataBook = createDataBook(request, response);

            if (requestType.equals(REQ_CHART_IMAGE)) {
                createChartImage(request, response, dataBook);
            } else if (requestType.equals(REQ_TABLE)) {
                    createTableModel(request, response, dataBook);
            } else {
                throw new Exception("The request being processed didn't"
                                    + " have " + "a recognized type ("
                                    + requestType + ")");
            }
        } catch (Exception re) {
            throw new
                ServletException("Unable to continue generating the report"
                                 + "as a report exception was encountered: "
                                 + re.getClass().getName() + ", "
                                 + re.getMessage()
                                 + " at "
                                 + re.getStackTrace()[0].getFileName() + ":"
                                 + re.getStackTrace()[0].getLineNumber());
        }
        return mav;
    }

    /**
     * Creates a reportable DataBook according to the current request.
     * @param req                the request
     * @param resp                the response
     * @return                        a new (and completed) DataBook
     * @throws Exception        if something happens
     */
    private synchronized DataBook createDataBook(final HttpServletRequest req,
            final HttpServletResponse resp)
            throws Exception {

        String reportFor = req.getParameter(PARAM_REPORT_FOR);

        String stringID = req.getParameter(PARAM_ID);
        if (stringID == null) {
            throw new ServletException("Missing ID parameter"
                                       + " for ReportController");
        }

        String courseId = (String) req.getSession().getAttribute("course");
        if (courseId == null) {
            throw new Exception("Can't get session variable");
        }

        businessLogic.setRequestType(reportFor);
        businessLogic.setCourseId(courseId);
        businessLogic.setId(stringID);
        if (log.isDebugEnabled()) {
            log.debug("Set reportFor as " + reportFor
                      + " and stringId as " + stringID
                      + " in businessLogic");
        }
        DataBook dataBook = businessLogic.buildDataBook();
        return dataBook;
    }

    private Map<String, Object>
        convertToMap(final List<Object> data,
                     final List<String> categories) {
        Map<String, Object> values = new HashMap<String, Object>();
        int c = 0;
        for (Object o : data) {
        if (log.isDebugEnabled()) {
            log.debug("Converting " + categories.get(c)
                      + ", " + o.toString() + " to a map");
        }
            values.put(categories.get(c++), o);
        }
        return values;
    }

    /**
     * Creates a table model, basically putting together a set
     * of values that can be retrieved through the reporting
     * system through the expression language in JSTL.
     *
     * @param req                the request
     * @param resp                the response
     * @param dataBook        the DataBook
     * @throws Exception        if something bad happens
     */
    private void createTableModel(final HttpServletRequest req,
                                  final HttpServletResponse resp,
                                  final DataBook dataBook)
        throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<String> categories = dataBook.getDataPoints();
        List actualComments = dataBook.getDataSeries("ActualComments");

        Map<String, List> commentValues = new HashMap<String, List>();
        int c = 0;
        for (Object comments: actualComments) {
            commentValues.put(categories.get(c++), (List<String>)comments);
        }

        List<Object> actualRange = dataBook.getDataSeries("ActualRange");
        List<Object> idealRange = dataBook.getDataSeries("IdealRange");
        List<Object> actualCounts = dataBook.getDataSeries("ActualCounts");
        List<Object> idealCounts = dataBook.getDataSeries("IdealCounts");

        model.put("categories", categories);
        model.put("actual", convertToMap(actualRange, categories));
        model.put("ideal", convertToMap(idealRange, categories));
        model.put("actualcounts", convertToMap(actualCounts, categories));
        model.put("idealcounts", convertToMap(idealCounts, categories));
        model.put("comments", commentValues);
        model.put("full", req.getParameter("full"));
        model.put("type", req.getParameter(PARAM_REPORT_FOR));
        model.put("id", req.getParameter(PARAM_ID));

        mav = new ModelAndView("report", model);
    }


    /**
     * Describe <code>createChartImage</code> method here.
     *
     * @param req a <code>HttpServletRequest</code> value
     * @param resp a <code>HttpServletResponse</code> value
     * @param dataBook a <code>DataBook</code> value
     * @exception Exception if an error occurs
     */
    private void createChartImage(final HttpServletRequest req,
                                  final HttpServletResponse resp,
                                  final DataBook dataBook)
        throws Exception {

        if (log.isDebugEnabled()) {
            log.debug("About to create chart image");
        }

        //calling to generate chart
        ReportRenderEngine rre = new
            ReportRenderEngine(req.getParameter(PARAM_CHART_TYPE),
                               dataBook);
        List<ReportConstituent> v = rre.renderRequest();

        ReportConstituent c = v.get(0);
        ChartConstituent chart = (ChartConstituent) c;
        chart.writeChartToStream(resp.getOutputStream(),
                                 ChartConstituent.JPEG_MIME_TYPE);
    }
}
