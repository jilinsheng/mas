package com.mingda.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExcel {
	static Logger log = Logger.getLogger(ExportExcel.class);
	private int LENGTH = 10000;

	@SuppressWarnings("rawtypes")
	public ByteArrayOutputStream genExcelData(HashMap title, List<HashMap> rs) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		Iterator columes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			columes = title.keySet().iterator();
			if (null != rs && rs.size() > 0) {

				int sheetcnt = rs.size() / LENGTH;
				int restcnt = rs.size() % LENGTH;

				if (sheetcnt > 0) {
					for (int i = 0; i < sheetcnt; i++) {
						sheet = workbook.createSheet("第" + (i + 1) + "页");
						row = sheet.createRow(0);
						int c = 0;
						columes = title.keySet().iterator();
						while (columes.hasNext()) {
							String tempcolname = columes.next().toString();
							String colname = tempcolname;
							String colvalue = (String) title.get(colname);
							cell = row.createCell(c);
							cell.setCellValue(colvalue);
							c++;
						}
						for (int j = i * LENGTH; j < (i + 1) * LENGTH; j++) {
							row = sheet.createRow(j - (i * LENGTH) + 1);
							HashMap map = (HashMap) rs.get(j);
							c = 0;
							columes = title.keySet().iterator();
							while (columes.hasNext()) {
								String[] tempcolname = columes.next()
										.toString().split(",");
								String colname = tempcolname[0];
								Object colvalue = (Object) map.get(colname);
								cell = row.createCell(c);
								if (null != colvalue) {
									if ("java.math.BigDecimal".equals(colvalue
											.getClass().getName())) {
										if ("dict".equals(tempcolname[1])) {
											cell.setCellValue(Dictionary
													.getInstance()
													.findDictvalue(colvalue));
										} else {
											cell.setCellValue(colvalue
													.toString());
										}
									} else if ("java.sql.Timestamp"
											.equals(colvalue.getClass()
													.getName())) {
										cell.setCellValue(colvalue.toString()
												.substring(0, 10));
									} else {
										cell.setCellValue(colvalue.toString());
									}
								}
								c++;
							}
						}
					}

					if (restcnt > 0) {
						sheet = workbook.createSheet("第" + (sheetcnt + 1) + "页");
						row = sheet.createRow(0);
						int c = 0;
						columes = title.keySet().iterator();
						while (columes.hasNext()) {
							String tempcolname = columes.next().toString();
							String colname = tempcolname;
							String colvalue = (String) title.get(colname);
							cell = row.createCell(c);
							cell.setCellValue(colvalue);
							c++;
						}
						for (int j = sheetcnt * LENGTH; j < (sheetcnt * LENGTH )+ restcnt; j++) {
							row = sheet.createRow(j - (sheetcnt * LENGTH) + 1);
							HashMap map = (HashMap) rs.get(j);
							c = 0;
							columes = title.keySet().iterator();
							while (columes.hasNext()) {
								String[] tempcolname = columes.next()
										.toString().split(",");
								String colname = tempcolname[0];
								Object colvalue = (Object) map.get(colname);
								cell = row.createCell(c);
								if (null != colvalue) {
									if ("java.math.BigDecimal".equals(colvalue
											.getClass().getName())) {
										if ("dict".equals(tempcolname[1])) {
											cell.setCellValue(Dictionary
													.getInstance()
													.findDictvalue(colvalue));
										} else {
											cell.setCellValue(colvalue
													.toString());
										}
									} else if ("java.sql.Timestamp"
											.equals(colvalue.getClass()
													.getName())) {
										cell.setCellValue(colvalue.toString()
												.substring(0, 10));
									} else {
										cell.setCellValue(colvalue.toString());
									}
								}
								c++;
							}
						}
					}
				} else {
					sheet = workbook.createSheet("第一页");
					row = sheet.createRow(0);
					int c = 0;
					columes = title.keySet().iterator();
					while (columes.hasNext()) {
						String tempcolname = columes.next().toString();
						String colname = tempcolname;
						String colvalue = (String) title.get(colname);
						cell = row.createCell(c);
						cell.setCellValue(colvalue);
						c++;
					}

					for (int j = sheetcnt * LENGTH; j < restcnt; j++) {
						row = sheet.createRow(j - (sheetcnt * LENGTH) + 1);
						HashMap map = (HashMap) rs.get(j);
						c = 0;
						columes = title.keySet().iterator();
						while (columes.hasNext()) {
							String[] tempcolname = columes.next().toString()
									.split(",");
							String colname = tempcolname[0];
							Object colvalue = (Object) map.get(colname);
							cell = row.createCell(c);
							if (null != colvalue) {
								if ("java.math.BigDecimal".equals(colvalue
										.getClass().getName())) {
									if ("dict".equals(tempcolname[1])) {
										cell.setCellValue(Dictionary
												.getInstance().findDictvalue(
														colvalue));
									} else {
										cell.setCellValue(colvalue.toString());
									}
								} else if ("java.sql.Timestamp".equals(colvalue
										.getClass().getName())) {
									cell.setCellValue(colvalue.toString()
											.substring(0, 10));
								} else {
									cell.setCellValue(colvalue.toString());
								}
							}
							c++;
						}
					}
				}
			}
			workbook.write(baos);
		} catch (RuntimeException re) {
			re.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baos;
	}

	public ByteArrayOutputStream genExcelData(List<HSSFRow> unlist) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HSSFSheet sheet = null;
		try {
			sheet = workbook.createSheet("导入没有成功数据");
			int i = 0;
			int j = 0;
			for (HSSFRow srow : unlist) {
				HSSFRow erow = sheet.createRow(i);
				if (i == 0) {
					j = srow.getPhysicalNumberOfCells();
				}
				for (int k = 0; k < j; k++) {
					HSSFCell scell = erow.createCell(k);
					scell.setCellValue(getRowCellValue(srow.getCell(k)));
				}
				i++;
			}
			workbook.write(baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos;
	}

	private String getRowCellValue(HSSFCell aCell) {
		String strCell = "";
		String str = "";
		if (null != aCell) {
			int cellType = aCell.getCellType();

			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC: // Numeric
				BigDecimal bd = new BigDecimal(aCell.getNumericCellValue());
				strCell = bd.toString();
				return str += strCell + "";

			case HSSFCell.CELL_TYPE_STRING: // String
				strCell = aCell.getStringCellValue();
				return str += strCell + "";

			case HSSFCell.CELL_TYPE_FORMULA: // formula
				strCell = String.valueOf(aCell.getNumericCellValue());
				return str += strCell + "";

			case HSSFCell.CELL_TYPE_BLANK:// blank
				strCell = aCell.getStringCellValue();
				return str += strCell + "";

			default:
				log.debug("格式读入不正确！");// 其它格式的数据
			}
		} else {
			return "";
		}
		return "";
	}
}
