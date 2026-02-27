package com.mc.back.sigrecette.service.impl;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.html.WebColors;
import com.lowagie.text.pdf.*;
import com.mc.back.sigrecette.model.LogData;
import com.mc.back.sigrecette.repository.ILogDataRepository;
import com.mc.back.sigrecette.security.JwtSecurity;
import com.mc.back.sigrecette.service.ICommonDao;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.tools.*;
import com.mc.back.sigrecette.tools.model.DaoObject;
import com.mc.back.sigrecette.tools.model.SearchObject;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CommonService implements ICommonService {
    @Value("${assets.pdf-header}")
    private String pdfHeaderUrl;

    @Value("${assets.pdf-header2}")
    private String pdfHeaderUrl2;
    private static final Logger logger = LogManager.getLogger(CommonService.class);

    @Autowired
    private ILogDataRepository logDataRepository;

    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private ICommonDao commonDao;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public SendObject getListPaginator(SearchObject objX, Object objClass, String particularSpecifCondi) {
        try {
            final SearchObject obj = new UtilsDao().initSearchObject(objX, objClass);

            CompletableFuture<DaoObject> cfL = CompletableFuture
                    .supplyAsync(() -> commonDao.getListPaginator(obj, objClass, particularSpecifCondi));
            CompletableFuture<DaoObject> cfC = CompletableFuture
                    .supplyAsync(() -> commonDao.getCountPaginator(obj, objClass, particularSpecifCondi));
            DaoObject daoL = cfL.get();
            DaoObject daoC = cfC.get();

            DaoObject daoObject = new DaoObject(daoL.getCode(), daoL.getObjectReturn(), daoC.getCountTotal());

            if (!daoObject.getCode().equals(ConstanteService._CODE_DAO_SUCCESS))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());

            return utilsWs.resultPaginationWs(ConstanteWs._CODE_WS_SUCCESS, daoObject.getObjectReturn(),
                    daoObject.getCountTotal());
        } catch (Exception e) {
            logger.error("Error CommonService in method getListPaginator of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi) {
        try {
            obj = new UtilsDao().initSearchObjectNative(obj, objClass);
            DaoObject daoObject = commonDao.getListPaginatorNative(obj, objClass, particularSpecifCondi);
            if (!daoObject.getCode().equals(ConstanteService._CODE_DAO_SUCCESS))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
            return utilsWs.resultPaginationWs(ConstanteWs._CODE_WS_SUCCESS, daoObject.getObjectReturn(),
                    daoObject.getCountTotal());
        } catch (Exception e) {
            logger.error("Error CommonService in method getListPaginator of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getObjectById(Object objClass, String valueId, Boolean nativeSQL) {
        try {
            DaoObject daoObjcet = commonDao.getObjectById(objClass, valueId, nativeSQL, null);
            if (daoObjcet.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObjcet.getCode(), daoObjcet.getObjectReturn(), null);
            else
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getObjectById of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR, null, null);
        }
    }

    @Override
    public SendObject getObjectById(Object objClass, String valueId, String particularSpecifCondi, Boolean nativeSQL) {
        try {
            DaoObject daoObjcet = commonDao.getObjectById(objClass, valueId, nativeSQL, particularSpecifCondi);
            if (daoObjcet.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObjcet.getCode(), daoObjcet.getObjectReturn(), null);
            else
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getObjectById of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR, null, null);
        }
    }

    @Override
    public SendObject getListObject(Object objClass, SearchObject obj, Boolean nativeSQL) {
        try {
            DaoObject daoObjcet = commonDao.getListObject(obj, objClass, null, nativeSQL);
            if (daoObjcet.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObjcet.getCode(), daoObjcet.getObjectReturn(), null);
            else
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getListObject of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR, null, null);
        }
    }

    @Override
    public SendObject getListObject(Object objClass, SearchObject obj, String particularSpecifCondi,
                                    Boolean nativeSQL) {
        try {
            DaoObject daoObjcet = commonDao.getListObject(obj, objClass, particularSpecifCondi, nativeSQL);
            if (daoObjcet.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObjcet.getCode(), daoObjcet.getObjectReturn(), null);
            else
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getListObject of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR, null, null);
        }
    }

    @Override
    public SendObject getUniqueCode(Object objClass, String colCode, Object idValue, String codeValue) {
        try {
            DaoObject daoObject = commonDao.getUniqueCode(objClass, colCode,
                    (idValue != null ? idValue.toString() : null), codeValue);
            if (daoObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(((Boolean) daoObject.getObjectReturn() ? ConstanteService._CODE_SERVICE_SUCCESS
                        : ConstanteService._CODE_SERVICE_ERROR_UNIQUE_CODE), null, null);
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getListObject of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR, null, null);
        }
    }

    @Override
    public SendObject getSingleObject(Object objClass, String particularSpecifCondi, Boolean nativeSQL) {
        try {
            DaoObject daoObject = commonDao.getSingleObject(objClass, particularSpecifCondi, nativeSQL);
            if (daoObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObject.getCode(), daoObject.getObjectReturn());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null);
        } catch (Exception e) {
            logger.error("Error CommonService in method getSingleObject of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null);
        }
    }

    @Override
    public SendObject getDateSystemNow() {
        try {
            DaoObject daoObject = commonDao.getDateSystemNow();
            if (!daoObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return new SendObject(daoObject.getCode());
            Instant instant = (Instant) daoObject.getObjectReturn();
            Timestamp date = Timestamp.from(instant);
            return new SendObject(ConstanteService._CODE_SERVICE_SUCCESS, date);
        } catch (Exception e) {
            logger.error("Error CommonService in method getDateSystemNow :: " + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null);
        }
    }

    @Override
    public SendObject getDateSystemNowWs() {
        try {
            SendObject so = this.getDateSystemNow();
            return utilsWs.resultWs(so.getCode(), so.getPayload());
        } catch (Exception e) {
            logger.error("Error CommonService in method getDateSystemNowWs " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getObjectByIdWs(Object objClass, String valueId, Boolean nativeSQL) {
        try {
            SendObject so = this.getObjectById(objClass, valueId, nativeSQL);
            return utilsWs.resultWs(so.getCode(), so.getPayload());
        } catch (Exception e) {
            logger.error("Error CommonService in method getObjectByIdWs of class " + objClass.getClass().getName()
                    + " :: " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    private JSONObject putter(Tuple t) {
        JSONObject j = new JSONObject();
        t.getElements().forEach(col -> {
            String[] splitCol = col.getAlias().toLowerCase().split("_");
            if (splitCol.length > 1)
                for (int i = 1; i < splitCol.length; i++) {
                    splitCol[i] = String.valueOf(splitCol[i].charAt(0)).toUpperCase() + splitCol[i].substring(1);
                }
            j.put(String.join("", splitCol), t.get(col.getAlias()));
        });
        return j;
    }

    @Override
    public SendObject mapper(Object tt) {
        try {
            if (tt != null) {
                if (tt instanceof ArrayList) {
                    JSONArray list = new JSONArray();
                    for (Tuple t : (List<Tuple>) tt) {
                        list.put(putter(t));
                    }
                    return new SendObject(ConstanteWs._CODE_WS_SUCCESS, list);
                } else {
                    return new SendObject(ConstanteWs._CODE_WS_SUCCESS, putter((Tuple) tt));
                }
            } else
                return null;
        } catch (Exception e) {
            logger.error("Error CommonService in method mapper ::" + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_IN_METHOD, null);
        }
    }

    @Override
    public SendObject exportDataWs(SearchObject objX, Object objClass, String particularSpecifCondi) {
        try {
            objX.setPagination(null);

            final SearchObject obj = new UtilsDao().initSearchObject(objX, objClass);

            CompletableFuture<DaoObject> cfL = CompletableFuture
                    .supplyAsync(() -> commonDao.getListPaginator(obj, objClass, particularSpecifCondi));
            CompletableFuture<DaoObject> cfC = CompletableFuture
                    .supplyAsync(() -> commonDao.getCountPaginator(obj, objClass, particularSpecifCondi));
            DaoObject daoL = cfL.get();
            DaoObject daoC = cfC.get();

            DaoObject daoObject = new DaoObject(daoL.getCode(), daoL.getObjectReturn(), daoC.getCountTotal());

            if (!daoObject.getCode().equals(ConstanteService._CODE_DAO_SUCCESS))
                return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());

            if (objX.getTypeExport().equals(Constante.CODE_EXPORT_PDF)) {
                return this.generatePDFFile(objX, daoObject.getObjectReturn());

            } else if (objX.getTypeExport().equals(Constante.CODE_EXPORT_EXCEL)) {
                return this.generateExcelFile(objX, daoObject.getObjectReturn());

            }

            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (Exception e) {
            logger.error("Error CommonService in method exportDataWs of class " + objClass.getClass().getName() + " ::"
                    + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject exportDataNativeWs(SearchObject objX, Object objClass, String particularSpecifCondi) {
        try {
            objX.setPagination(null);
            final SearchObject obj = new UtilsDao().initSearchObjectNative(objX, objClass);

            CompletableFuture<DaoObject> cfL = CompletableFuture
                    .supplyAsync(() -> commonDao.getListPaginatorNative(obj, objClass, particularSpecifCondi));
            DaoObject daoL = cfL.get();
            DaoObject daoObject = new DaoObject(daoL.getCode(), daoL.getObjectReturn(), null);

            if (!daoObject.getCode().equals(ConstanteService._CODE_DAO_SUCCESS))
                return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());

            if (objX.getTypeExport().equals(Constante.CODE_EXPORT_PDF)) {
                return this.generatePDFFile(objX, daoObject.getObjectReturn());

            } else if (objX.getTypeExport().equals(Constante.CODE_EXPORT_EXCEL)) {
                return this.generateExcelFile(objX, daoObject.getObjectReturn());

            }

            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (Exception e) {
            logger.error("Error CommonService in method exportDataNativeWs of class {} ::{}", objClass.getClass().getName(), e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    private SendObject generateExcelFile(SearchObject objX, Object objectReturnDataList) {
        try {
            JSONArray dataList = new JSONArray();
            Field field = null;
            if (objectReturnDataList instanceof JSONArray) {
                dataList = (JSONArray) objectReturnDataList;
            } else if (objectReturnDataList instanceof ArrayList) {
                for (Object obj : ((ArrayList<?>) objectReturnDataList)) {
                    JSONObject jo = new JSONObject();
                    for (int i = 1; i < obj.getClass().getDeclaredFields().length; i++) {
                        field = obj.getClass().getDeclaredFields()[i];
                        field.setAccessible(true);
                        jo.put(field.getName(), field.get(obj));
                    }
                    dataList.put(jo);
                }
            }
            LinkedHashMap<String, Object> metadata = (LinkedHashMap<String, Object>) objX.getMetadata();

            List<LinkedHashMap<String, Object>> columns = ((List<LinkedHashMap<String, Object>>) metadata
                    .get("columns"));

            if (objX.getLanguage().equals(Constante.CODE_LANGUAGE_AR)) {
                Collections.reverse(columns);
            }

            List<String> columnsLabels = columns.stream().map(col -> col.get("label").toString())
                    .collect(Collectors.toList());

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet((String) metadata.get("title"));
            sheet.createFreezePane(0, 1, 0, 1);

            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            Row headerRow = sheet.createRow(0);
            headerRow.setHeight((short) 500);

            // create header row
            for (int colIdx = 0; colIdx < columnsLabels.size(); colIdx++) {
                Cell cell = headerRow.createCell(colIdx);
                cell.setCellValue(columnsLabels.get(colIdx));
                cell.setCellStyle(headerCellStyle);
            }

            // create data rows
            int rowIdx = 1;
            for (Object o : dataList) {
                JSONObject jo = (JSONObject) o;
                if (objX.getLanguage().equals(Constante.CODE_LANGUAGE_FR)) {
                    updateStatus(jo, "is_active", "Actif", "Inactif");
                    updateStatus(jo, "isActive", "Actif", "Inactif");
                } else {
                    updateStatus(jo, "is_active", "Active", "Inactive");
                    updateStatus(jo, "isActive", "Active", "Inactive");
                }

                Row row = sheet.createRow(rowIdx++);
                for (int colIdx = 0; colIdx < columns.size(); colIdx++) {
                    if (columns.get(colIdx).get("key") instanceof String) {
                        row.createCell(colIdx).setCellValue(
                                this.getFormattedValueForExport(jo, columns.get(colIdx), objX.getLanguage()));
                    } else if (columns.get(colIdx).get("key") instanceof LinkedHashMap) {
                        Object keyValue = null;
                        String cellValue = "";
                        LinkedHashMap<?, ?> columnMap = (LinkedHashMap<?, ?>) columns.get(colIdx).get("key");

                        if (columnMap != null && columnMap.containsKey(objX.getLanguage())) {
                            keyValue = columnMap.get(objX.getLanguage());
                            if (keyValue != null) {
                                cellValue = (jo.has(keyValue.toString())) ? jo.get(keyValue.toString()).toString() : " — ";
                            }
                        }

                        row.createCell(colIdx).setCellValue(cellValue);
                    }
                }
            }

            // Auto size all the columns
            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Write the book and close the outputStream
            File file = new File(metadata.get("title").toString());
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();

            return new SendObject(ConstanteService._CODE_SERVICE_SUCCESS, file);
        } catch (NoClassDefFoundError e) {
            logger.error("Error CommonService in method generateExcelFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (IllegalAccessException e) {
            logger.error("Error CommonService in method generateExcelFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (IOException e) {
            logger.error("Error CommonService in method generateExcelFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (Exception e) {
            logger.error("Error CommonService in method generateExcelFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    /// //////////////// PDF////////
    public SendObject generatePDFFile(SearchObject objX, Object objectReturnDataList) {
        try {
//adding data
            JSONArray dataList = new JSONArray();
            Field field = null;
            if (objectReturnDataList instanceof JSONArray) {
                dataList = (JSONArray) objectReturnDataList;
            } else if (objectReturnDataList instanceof ArrayList) {
                for (Object obj : ((ArrayList<?>) objectReturnDataList)) {
                    JSONObject jo = new JSONObject();
                    for (int i = 1; i < obj.getClass().getDeclaredFields().length; i++) {
                        field = obj.getClass().getDeclaredFields()[i];
                        field.setAccessible(true);
                        jo.put(field.getName(), field.get(obj));
                    }
                    dataList.put(jo);
                }
            }

            LinkedHashMap<String, Object> metadata = (LinkedHashMap<String, Object>) objX.getMetadata();
            List<LinkedHashMap<String, Object>> columns = ((List<LinkedHashMap<String, Object>>) metadata
                    .get("columns"));

            if (objX.getLanguage().equals(Constante.CODE_LANGUAGE_AR)) {
                Collections.reverse(columns);
            }

            List<String> columnsLabels = columns.stream().map(col -> col.get("label").toString())
                    .collect(Collectors.toList());

            Font font = new Font(Font.HELVETICA, 10, Font.BOLD, java.awt.Color.white);
            // Set the orientation
            Document doc = metadata.containsKey("exportOrientation") && Objects.equals(metadata.get("exportOrientation").toString(), "landscape")
                    ? new Document(PageSize.A4.rotate())
                    : new Document();

//        File file = new File(metadata.get("title").toString().trim());

            // Get the title and sanitize it
            String title = metadata.get("title").toString().trim();
            String sanitizedTitle = title.replaceAll("[<>:\"/\\\\|?*]", " "); // Replace invalid characters with a space

// Create the file object with the sanitized title
            File file = new File(System.getProperty("java.io.tmpdir") + sanitizedTitle);

            PdfWriter writer = PdfWriter.getInstance(doc, Files.newOutputStream(file.toPath()));


            doc.open();

            file = new File(System.getProperty("java.io.tmpdir") + "pdfFile");

            PdfWriter writerpdf = PdfWriter.getInstance(doc, new FileOutputStream(file));
            // Set your custom page event handler
            PageNumberEventHandler eventHandler = new PageNumberEventHandler();
            writerpdf.setPageEvent(eventHandler);
            doc.open();

// Calculate column widths dynamically
            float[] columnWidths = new float[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                LinkedHashMap<String, Object> column = columns.get(i);
                String width = column.containsKey("export")
                        ? String.valueOf(((LinkedHashMap<?, ?>) column.get("export")).getOrDefault("width", null)).replace("*", "1")
                        : "1";

// Remove any non-numeric characters (e.g., %) before parsing the float
                try {
                    if (width == null || width.trim().isEmpty()) {
                        columnWidths[i] = 1.0f; // Default value if width is null or empty
                    } else if ("*".equals(width)) {
                        columnWidths[i] = -1f; // Flexible width
                    } else {
                        String widthWithoutPercent = width.replace("%", ""); // Remove "%" sign
                        columnWidths[i] = Float.parseFloat(widthWithoutPercent);
                    }// Parse the value

                } catch (NumberFormatException e) {
                    // Handle the case where parsing fails (e.g., invalid format)
                    System.out.println("Invalid number format for width: " + width);
                    columnWidths[i] = 1.0f;  // Default value in case of error
                }
            }
//Creating font
            Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
            fontTiltle.setSize(20);
            Font fontt = new Font(Font.HELVETICA, 12, Font.ITALIC, java.awt.Color.black);
            Image image = null;
            String headerUrl = (objX.getLanguage().equals(Constante.CODE_LANGUAGE_FR)) ? this.pdfHeaderUrl : this.pdfHeaderUrl2;

            image = Image.getInstance(headerUrl);
            image.scaleAbsolute(600, 80);
            image.setAlignment(Element.ALIGN_CENTER);
            doc.add(image);
            Paragraph esp = new Paragraph("\n");
            doc.add(new Paragraph(esp));

            Font TitleFont = new Font(Font.HELVETICA, 15, Font.BOLD, java.awt.Color.black);

            Paragraph titre = new Paragraph(metadata.get("title").toString(), TitleFont);
            titre.setAlignment(Element.ALIGN_CENTER);
            doc.add(new Paragraph(titre));
            Paragraph titre2 = new Paragraph("\n");
            doc.add(new Paragraph(titre2));

            Calendar cals = Calendar.getInstance();
//Displaying the actual date

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            String Date = (objX.getLanguage().equals(Constante.CODE_LANGUAGE_FR)) ? "Tunis le " + strDate : "Tunis, " + strDate;
            Paragraph calendrier = new Paragraph(Date);
            calendrier.setAlignment(Element.ALIGN_RIGHT);

//Creating table
            doc.add(calendrier);

            doc.add(new Paragraph(titre2));

            PdfPTable table = new PdfPTable(columnsLabels.size());
            table.setWidthPercentage(100);
            table.setWidths(columnWidths);

//Creating headers rows
            for (int colIdx = 0; colIdx < columnsLabels.size(); colIdx++) {
                PdfPCell cell = new PdfPCell();
                java.awt.Color myColor = WebColors.getRGBColor("#1581b3");
                cell.setBackgroundColor(myColor);

                cell.setPadding(5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                cell.setPhrase(new Phrase(columnsLabels.get(colIdx), font));
                table.addCell(cell);
            }


//create data rows
            Font fontRow = new Font(Font.TIMES_ROMAN, 11, Font.NORMAL);

            boolean hasRowNumbers = columns.stream()
                    .anyMatch(col -> "rowNumbers".equals(col.get("key")));

// Track row numbers dynamically
            int rowNumber = 1;
            for (Object o : dataList) {
                JSONObject jo = (JSONObject) o;
                if (objX.getLanguage().equals(Constante.CODE_LANGUAGE_FR)) {
                    updateStatus(jo, "is_active", "Actif", "Inactif");
                    updateStatus(jo, "isActive", "Actif", "Inactif");
                } else {
                    updateStatus(jo, "is_active", "Active", "Inactive");
                    updateStatus(jo, "isActive", "Active", "Inactive");
                }
                if (objX.getLanguage().equals(Constante.CODE_LANGUAGE_FR)) {
                    valid(jo, "is_valid", "Oui", "Non");
                    valid(jo, "isValid", "Oui", "Non");
                } else {
                    valid(jo, "is_valid", "true", "false");
                    valid(jo, "isValid", "true", "false");
                }

//           for (int colIdx = 0; colIdx < columns.size(); colIdx++) {
//              if (columns.get(colIdx).get("key") instanceof String) {
//                 Phrase phrase = new Phrase(
//                       this.getFormattedValueForExport(jo, columns.get(colIdx), objX.getLanguage()), fontRow);
//                 PdfPCell cell = new PdfPCell(phrase);
//                 cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//                 table.addCell(cell);
//              } else if (columns.get(colIdx).get("key") instanceof LinkedHashMap) {
//                 Object keyValue = null;
//                 String textValue = " — ";
//                 LinkedHashMap<?, ?> columnMap = (LinkedHashMap<?, ?>) columns.get(colIdx).get("key");
//                 if (columnMap != null && columnMap.containsKey(objX.getLanguage())) {
//                    keyValue = columnMap.get(objX.getLanguage());
//                    textValue = (jo.has(keyValue.toString())) ? jo.get(keyValue.toString()).toString() : " — ";
//                 }
//                 Phrase phrase = new Phrase(textValue, fontRow);
//
//                 table.addCell(new PdfPCell(phrase));
//              }
//           }
                // Add the row number as the first cell
                // Add row number column if it exists
                if (hasRowNumbers) {
                    PdfPCell rowNumCell = new PdfPCell(new Phrase(String.valueOf(rowNumber++), fontRow));
                    rowNumCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(rowNumCell);
                }


                for (int colIdx = 0; colIdx < columns.size(); colIdx++) {
                    Map<String, Object> column = columns.get(colIdx);
                    String key = column.get("key") instanceof String ? (String) column.get("key") : null;

                    PdfPCell cell;
                    // Skip rowNumbers column in metadata to avoid duplication
                    if ("rowNumbers".equals(key)) {
                        continue;
                    }
                    String alignment = "left"; // Default alignment
                    int alignmentValue = Element.ALIGN_LEFT; // Default alignment value


                    // Check if the column has export metadata with textAlign property
                    Map<String, Object> exportMetadata = (Map<String, Object>) columns.get(colIdx).get("export");
                    if (exportMetadata != null && exportMetadata.containsKey("textAlign")) {
                        alignment = (String) exportMetadata.get("textAlign");

                        // Map alignment string to PDF alignment value
                        if ("center".equalsIgnoreCase(alignment)) {
                            alignmentValue = Element.ALIGN_CENTER;
                        } else if ("right".equalsIgnoreCase(alignment)) {
                            alignmentValue = Element.ALIGN_RIGHT;
                        } else {
                            alignmentValue = Element.ALIGN_LEFT;
                        }
                    }


                    if (columns.get(colIdx).get("key") instanceof String) {
                        // Handle String keys
                        Phrase phrase = new Phrase(
                                this.getFormattedValueForExport(jo, columns.get(colIdx), objX.getLanguage()), fontRow);
                        cell = new PdfPCell(phrase);
                        cell.setHorizontalAlignment(alignmentValue);
                        table.addCell(cell);
                    } else if (columns.get(colIdx).get("key") instanceof LinkedHashMap) {
                        // Handle LinkedHashMap keys
                        Object keyValue = null;
                        String textValue = " — ";
                        LinkedHashMap<?, ?> columnMap = (LinkedHashMap<?, ?>) columns.get(colIdx).get("key");
                        if (columnMap != null && columnMap.containsKey(objX.getLanguage())) {
                            keyValue = columnMap.get(objX.getLanguage());
                            textValue = (jo.has(keyValue.toString())) ? jo.get(keyValue.toString()).toString() : " — ";
                        }

                        Phrase phrase = new Phrase(textValue, fontRow);
                        cell = new PdfPCell(phrase);
                        cell.setHorizontalAlignment(alignmentValue);
                        table.addCell(cell);
                    }
                }
            }
            doc.add(table);
            doc.close();
            writer.close();
            return new SendObject(ConstanteService._CODE_SERVICE_SUCCESS, file);
        } catch (NoClassDefFoundError e) {
            logger.error("Error CommonService in method generatePDFFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (IOException e) {
            logger.error("Error CommonService in method generatePDFFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        } catch (Exception e) {
            logger.error("Error CommonService in method generatePDFFile ::" + e.toString());
            return new SendObject(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    public void generate(HttpServletResponse response) throws DocumentException, IOException {
        // Create the Object of Document
        Document document = new Document(PageSize.A4);
        // get the document and write the response to output stream
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
    }

    private String getFormattedValueForExport(JSONObject dataObj, LinkedHashMap<String, Object> column,
                                              String language) {

        Object value = "";

        if (column.get("key") instanceof String) {
            value = dataObj.has(column.get("key").toString()) ? dataObj.get(column.get("key").toString()) : "";
        }

        if (value == null || value == JSONObject.NULL || (value.equals("") && !column.containsKey("type"))
                || (value.equals("") && column.containsKey("type")
                && !column.get("type").equals(Constante.CODE_COMBINED))) {
            return "-";
        }

        if (!column.containsKey("type")) {
            return value.toString();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                language.equals(Constante.CODE_LANGUAGE_AR) ? "YYYY/MM/dd" : "dd/MM/YYYY");
        SimpleDateFormat simpleDatetimeFormat = new SimpleDateFormat(
                language.equals(Constante.CODE_LANGUAGE_AR) ? "YYYY/MM/dd" : "dd/MM/YYYY");
        NumberFormat montantFormat = NumberFormat.getInstance(Locale.FRENCH);
        montantFormat.setMaximumFractionDigits(2);
        switch (column.get("type").toString()) {
            case Constante.CODE_DATE:
                return simpleDateFormat.format(value);
            case Constante.CODE_DATETIME:
                return simpleDatetimeFormat.format(value);
            case Constante.CODE_MONTANT:
                return montantFormat.format(value);
            case Constante.CODE_COMBINED:
                StringBuilder result = new StringBuilder();
                logger.info(column.get("combination"));

                List<LinkedHashMap<String, Object>> combination = new ArrayList<>();
                for (Object element : (List<Object>) column.get("combination")) {
                    logger.info(element);
                    combination.add((LinkedHashMap<String, Object>) element);
                }

                // combination.put(column.get("combination"));

                if (combination != null && !combination.isEmpty()) {
                    for (LinkedHashMap<String, Object> combObj : combination) {
                        if (combObj.get("key") instanceof String) {
                            result.append(this.getFormattedValueForExport(dataObj, combObj, language));
                            if (combObj.get("sep") != null) {
                                result.append(combObj.get("sep"));
                            }
                        } else if (combObj.get("key") instanceof LinkedHashMap) {
                            result.append(dataObj.get(((LinkedHashMap<?, ?>) combObj.get("key")).get(language).toString()));
                            if (combObj.get("sep") != null) {
                                result.append(combObj.get("sep"));
                            }
                        }

                    }
                }

                return result.toString();
            case Constante.CODE_TEXT:
            default:
                return value.toString();
        }
    }

    public Object getObjectToSave(Object newEntity, Object oldEntity) {
        try {
            Field field = null;
            for (int i = 1; i < newEntity.getClass().getDeclaredFields().length; i++) {
                field = newEntity.getClass().getDeclaredFields()[i];
                field.setAccessible(true);
                if (field.get(newEntity) == null) {
                    PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(newEntity);
                    myAccessor.setPropertyValue(field.getName(), field.get(oldEntity));
                }
            }
            return newEntity;
        } catch (Exception e) {
            logger.error("Error CommonService in method getEntityToSave ::" + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_IN_METHOD, null);
        }
    }

    @Override
    public SendObject isUnicode(DataAccessException ex) {
        try {

            if (ex.getRootCause() instanceof SQLException) {
                SQLException sqlException = (SQLException) ex.getRootCause();
                String sqlState = sqlException.getSQLState();
                if (Constante.CODE_SQL_CODE_DUPLICATED.equals(sqlState)) {
                    return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_UNIQUE_CODE, new JSONObject());
                } else
                    return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_SAVE_OR_UPDATE, new JSONObject());
            } else
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_IN_METHOD, null);

        } catch (Exception e) {
            logger.error("Error CommonService in method isUnicode ::" + e.toString());
            return new SendObject(ConstanteService._CODE_SERVICE_ERROR_IN_METHOD, null);
        }
    }

    private void updateStatus(JSONObject jo, String key, String activeValue, String inactiveValue) {
        if (jo.has(key)) {
            int value = jo.getInt(key);
            if (value == 1) {
                jo.put(key, activeValue);
            } else if (value == 0) {
                jo.put(key, inactiveValue);
            }
        }
    }

    private void valid(JSONObject jo, String key, String activeValue, String inactiveValue) {
        if (jo.has(key)) {
            boolean value = jo.getBoolean(key);
            if (value) {
                jo.put(key, activeValue);
            } else {
                jo.put(key, inactiveValue);
            }
        }
    }

    static class PageNumberEventHandler extends PdfPageEventHelper {
        // Define 'total' as an instance variable
        private PdfTemplate total;

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            // Initialize the template for total pages when the document is opened
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Add the page number and total pages count in the format "Page X of Y"
            Rectangle rect = document.getPageSize();
            String text = " " + writer.getPageNumber() + " / ";
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
                    new Phrase(text, new Font(Font.HELVETICA, 8)),
                    rect.getRight() - 63, rect.getBottom() + 15, 0);

            // Add the total pages template
            PdfContentByte cb = writer.getDirectContent();
            cb.addTemplate(total, rect.getRight() - 60, rect.getBottom() + 15);
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document) {
            // Fill the total number of pages in the template once the document is closed
            total.beginText();
            try {
                total.setFontAndSize(BaseFont.createFont(), 8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            total.setTextMatrix(0, 0);
            total.showText(String.valueOf(writer.getPageNumber() - 1));
            total.endText();
        }
    }

    @Override
    public void saveLogAccess(ServerHttpRequest request, String code) {
        LogData logData = new LogData();
        logData.setIdUser(jwtSecurity.getIdFromToken(request.getHeaders().getOrEmpty("Authorization").get(0)));
        logData.setDateLog(logDataRepository.getDateSystemNow());
        logData.setHttpMethod(request.getMethod().toString());
        logData.setUri(request.getPath().toString());
        logData.setResultWs(code);
        if (request.getRemoteAddress() != null)
            logData.setIpAddress(request.getRemoteAddress().getAddress() + ":" + request.getRemoteAddress().getPort());
        logDataRepository.save(logData);
    }
}