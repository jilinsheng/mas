package com.mingda.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.mingda.common.Pager;
import com.mingda.dao.ExportxlsDAO;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.ExtendsncDAO;
import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.ExportxlsDTO;
import com.mingda.dto.MemberBaseinfoviewDTO;
import com.mingda.model.Exportxls;
import com.mingda.model.ExportxlsExample;

public class SearchServiceImpl implements SearchService {
	static Logger log = Logger.getLogger(SearchServiceImpl.class);
	private ExportxlsDAO exportxlsDAO;
	private ExtendsDAO extendsDAO;
	private ExtendsncDAO extendsncDAO;
	private Pager pager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HashMap> findAll(String sql) {
		HashMap params = new HashMap();
		params.put("executsql", sql);
		return extendsDAO.queryAll(params);
	}

	/**
	 * 住院未审核
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BizCheckDTO> findNotCheck(String organizationId,
			int currentpage, String url) {
		List<BizCheckDTO> list = new ArrayList<BizCheckDTO>();
		String sql = "select biz.biz_id, biz.ssn, d.name as hname, "
				+ " biz.biz_type,   biz.family_no,  biz.name,biz.dept_name,biz.area_name,biz.begin_time,biz.diagnose_name, "
				+ " biz.id_card" 
				//+ ",biz.pay_ex as MONEYSTAND"
				+ " from jz_biz biz, bizdept d "
				+ " where d.hospital_id(+) = biz.hospital_id"
				+ " and biz.biz_type = 2 and biz.reg_status=1 and ( biz.assist_flag = 0 or  biz.assist_flag = 2 ) and biz.reg_status=1 and biz.REG_STATUS=1 and biz.family_no like '"
				+ organizationId
				+ "%' order by biz.begin_time desc , biz.family_no";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			BizCheckDTO e = new BizCheckDTO();
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			Date BEGIN_TIME = (Date) s.get("BEGIN_TIME");
			String DIAGNOSE_NAME = (String) s.get("DIAGNOE_NAME");
			BigDecimal MONEYSTAND = (BigDecimal) s.get("MONEYSTAND");
			// 计算天数
			long days = 0;
			if (null != BEGIN_TIME) {
				Date now = new Date();
				days = (now.getTime() - BEGIN_TIME.getTime())
						/ (24 * 60 * 60 * 1000);
			}
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setHname(HNAME);
			e.setDeptName(DEPT_NAME);
			e.setAreaName(AREA_NAME);
			e.setBeginTime(BEGIN_TIME);
			e.setDiagnoseName(DIAGNOSE_NAME);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			e.setDays(String.valueOf(days));
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE)) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE)) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE)) {
				e.setBizType("购药");
			}
			e.setMondeystand(MONEYSTAND);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MemberBaseinfoviewDTO> findCityMembers(String sql,
			int currentpage, String url) {
		List<MemberBaseinfoviewDTO> list = new ArrayList<MemberBaseinfoviewDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			MemberBaseinfoviewDTO e = new MemberBaseinfoviewDTO();
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSsn((String) s.get("SSN"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setFamilyid((String) s.get("FAMILYID"));
			String DS = (String) s.get("DS");
			e.setRpraddress((String) s.get("ORGNAME"));
			e.setDs(DS);
			String medicaretype = (String) s.get("MEDICARE_TYPE");
			String medicaretypetext = "";
			if("1".equals(medicaretype)){
				medicaretypetext = "医保";
			}else if("2".equals(medicaretype)){
				medicaretypetext = "新农合";
			}else if("0".equals(medicaretype)){
				medicaretypetext = "未参保/参合";
			}else{
				medicaretypetext = "未知";
			}
			e.setMedicaretype(medicaretype);
			e.setMedicaretypetext(medicaretypetext);
			String ASSIST_TYPE = (String) s.get("ASSIST_TYPE");
			e.setAssistType(ASSIST_TYPE);
			String dsval = "";
			String a1 = ASSIST_TYPE.substring(0, 1);
			String a2 = ASSIST_TYPE.substring(1, 2);
			String a3 = ASSIST_TYPE.substring(2, 3);
			String a4 = ASSIST_TYPE.substring(3, 4);
			String a5 = ASSIST_TYPE.substring(4, 5);
			String ASSIST_TYPEX = (String) s.get("ASSIST_TYPEX");
			String a6 = ASSIST_TYPEX.substring(0,1);
			/*String a7 = ASSIST_TYPEX.substring(1,2);
			String a8 = ASSIST_TYPEX.substring(2,3);
			String a9 = ASSIST_TYPEX.substring(3,4);
			String a10 = ASSIST_TYPEX.substring(4,5);
			String a11 = ASSIST_TYPEX.substring(5,6);*/
			if ("1".equals(DS)) {
				if ("1".equals(a1)) {
					dsval = dsval + "城市低保户;";
				}
				if ("1".equals(a2)) {
					dsval = dsval + "分类施保;";
				}
				if ("1".equals(a3)) {
					dsval = dsval + "三无家庭;";
				}
				if ("1".equals(a4)) {
					dsval = dsval + "五保户;";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象;";
				}
				if ("1".equals(a6)) {
					dsval = dsval + "孤儿;";
				}
			} else if ("2".equals(DS)) {
				if ("2".equals(a1)) {
					dsval = dsval + "农村低保一般户;";
				}
				if ("1".equals(a2)) {
					dsval = dsval + "重点户;";
				}
				if ("1".equals(a3)) {
					dsval = dsval + "三无家庭;";
				}
				if ("1".equals(a4)) {
					dsval = dsval + "五保户;";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象;";
				}
				if ("1".equals(a6)) {
					dsval = dsval + "孤儿;";
				}
			}
			if("00000".equals(ASSIST_TYPE) && "000000".equals(ASSIST_TYPEX)){
				dsval = "普通居民";
			}
			e.setDsval(dsval);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MemberBaseinfoviewDTO> findTownMembers(String sql,
			int currentpage, String url) {
		// MEMBER_ID MEMBERNAME PAPERID SSN FAMILYID FAMILYNO RELMASTER SEX
		// MASTERNAME BIRTHDAY HEALTH SICKENTYPE SICKENNAME DEFORMITY DEFGRADE
		// RPRKIND RPRTYPE RPRADDRESS LINKMODE ADDRESS PERSONSTATE ASSIST_TYPE
		// DS SALVATION_ID MEDICARETYPE
		List<MemberBaseinfoviewDTO> list = new ArrayList<MemberBaseinfoviewDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			MemberBaseinfoviewDTO e = new MemberBaseinfoviewDTO();
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSsn((String) s.get("SSN"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setFamilyid((String) s.get("FAMILYID"));
			e.setRpraddress((String) s.get("ORGNAME"));
			String DS = (String) s.get("DS");
			e.setDs(DS);
			String medicaretype = (String) s.get("MEDICARE_TYPE");
			String medicaretypetext = "";
			if("1".equals(medicaretype)){
				medicaretypetext="医保";
			}else if("2".equals(medicaretype)){
				medicaretypetext="新农合";
			}else if("0".equals(medicaretype)){
				medicaretypetext="未参保/参合";
			}else{
				medicaretypetext="未知";
			}
			e.setMedicaretype(medicaretype);
			e.setMedicaretypetext(medicaretypetext);
			String ASSIST_TYPE = (String) s.get("ASSIST_TYPE");
			e.setAssistType(ASSIST_TYPE);
			String dsval = "";
			String a1 = ASSIST_TYPE.substring(0, 1);
			String a2 = ASSIST_TYPE.substring(1, 2);
			String a3 = ASSIST_TYPE.substring(2, 3);
			String a4 = ASSIST_TYPE.substring(3, 4);
			String a5 = ASSIST_TYPE.substring(4, 5);
			String ASSIST_TYPEX = (String) s.get("ASSIST_TYPEX");
			String a6 = ASSIST_TYPEX.substring(0,1);
			/*String a7 = ASSIST_TYPEX.substring(1,2);
			String a8 = ASSIST_TYPEX.substring(2,3);
			String a9 = ASSIST_TYPEX.substring(3,4);
			String a10 = ASSIST_TYPEX.substring(4,5);
			String a11 = ASSIST_TYPEX.substring(5,6);*/
			if ("1".equals(DS)) {
				if ("1".equals(a1)) {
					dsval = dsval + "城市低保户;";
				}
				if ("1".equals(a2)) {
					dsval = dsval + "分类施保;";
				}
				if ("1".equals(a3)) {
					dsval = dsval + "三无家庭;";
				}
				if ("1".equals(a4)) {
					dsval = dsval + "五保户;";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象;";
				}
				if ("1".equals(a6)) {
					dsval = dsval + "孤儿;";
				}
			} else if ("2".equals(DS)) {
				if ("2".equals(a1)) {
					dsval = dsval + "农村低保一般户;";
				}
				if ("1".equals(a2)) {
					dsval = dsval + "重点户;";
				}
				if ("1".equals(a3)) {
					dsval = dsval + "三无家庭;";
				}
				if ("1".equals(a4)) {
					dsval = dsval + "五保户;";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象;";
				}
				if ("1".equals(a6)) {
					dsval = dsval + "孤儿;";
				}
			}
			if("00000".equals(ASSIST_TYPE) && "000000".equals(ASSIST_TYPEX)){
				dsval = "普通居民";
			}
			e.setDsval(dsval);
			if("1".equals(DS)){
				e.setDss("城市数据库");
			}else if("2".equals(DS)){
				e.setDss("农村数据库");
			}
			if(e.getFamilyid().equals(e.getMemberId())){
				e.setDss("五保/优抚/三无/孤儿数据库");
			}
			e.setDsval(dsval);
			list.add(e);
		}
		return list;
	}

	// 五保户导入部分
	public List<ExportxlsDTO> findEs(String empid) {
		List<ExportxlsDTO> list = new ArrayList<ExportxlsDTO>();
		ExportxlsExample example = new ExportxlsExample();
		List<String> values = new ArrayList<String>();
		values.add("1");
		values.add("2");
		example.createCriteria().andEmpIdEqualTo(empid).andEtypeIn(values);
		List<Exportxls> rs = exportxlsDAO.selectByExample(example);
		for (Exportxls s : rs) {
			ExportxlsDTO e = new ExportxlsDTO();
			e.setEid(s.getEid());
			e.setEmpId(s.getEmpId());
			e.setEtype(s.getEtype());
			e.setFilename(s.getFilename());
			e.setOpertime(s.getOpertime());
			e.setOrganizationId(s.getOrganizationId());
			e.setRealpath(s.getRealpath());
			e.setStatuts(s.getStatuts());
			e.setUploadtime(s.getUploadtime());
			list.add(e);
		}
		return list;
	}

	public List<ExportxlsDTO> findEs1(String empid) {
		List<ExportxlsDTO> list = new ArrayList<ExportxlsDTO>();
		ExportxlsExample example = new ExportxlsExample();
		example.createCriteria().andEmpIdEqualTo(empid).andEtypeEqualTo("3");
		List<Exportxls> rs = exportxlsDAO.selectByExample(example);
		for (Exportxls s : rs) {
			ExportxlsDTO e = new ExportxlsDTO();
			e.setEid(s.getEid());
			e.setEmpId(s.getEmpId());
			e.setEtype(s.getEtype());
			e.setFilename(s.getFilename());
			e.setOpertime(s.getOpertime());
			e.setOrganizationId(s.getOrganizationId());
			e.setRealpath(s.getRealpath());
			e.setStatuts(s.getStatuts());
			e.setUploadtime(s.getUploadtime());
			list.add(e);
		}
		return list;
	}

	public void saveUploadXLS(ExportxlsDTO exportxlsDTO) {
		Exportxls record = new Exportxls();
		record.setEtype(exportxlsDTO.getEtype());
		record.setFilename(exportxlsDTO.getFilename());
		record.setRealpath(exportxlsDTO.getRealpath());
		record.setUploadtime(exportxlsDTO.getUploadtime());
		record.setOpertime(exportxlsDTO.getOpertime());
		record.setOrganizationId(exportxlsDTO.getOrganizationId());
		record.setEmpId(exportxlsDTO.getEmpId());
		record.setStatuts(exportxlsDTO.getStatuts());
		this.exportxlsDAO.insertSelective(record);
	}

	public void removeUploadXLS(ExportxlsDTO exportxlsDTO) {
		Long eid = exportxlsDTO.getEid();
		Exportxls exportxls = exportxlsDAO.selectByPrimaryKey(eid);
		File file = new File("\\\\" + exportxls.getRealpath());
		file.delete();
		exportxlsDAO.deleteByPrimaryKey(eid);
	}

	/**
	 * 姓名 五保证号 性别 民族 生日 身份证号 入住时间 供养标准 人员类别 家庭住址 房屋间数 建筑面积 房屋结构 耕地面积 更重形式 年纯收入
	 * 土地收入 养殖收入 其它收入 乡镇 县市 机构编号 返回不成功列表
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<HSSFRow> saveExportexcel(ExportxlsDTO exportxlsDTO) {
		List<HSSFRow> unlist = new ArrayList<HSSFRow>();
		Long eid = exportxlsDTO.getEid();
		Exportxls exportxls = exportxlsDAO.selectByPrimaryKey(eid);
		String etype = exportxls.getEtype();
		String realpath = "\\\\" + exportxls.getRealpath();
		String sql = "";
		try {
			HSSFSheet sheet = getSheet(realpath, 0);
			// 首行加入
			unlist.add(sheet.getRow(0));
			int lastrownum = sheet.getPhysicalNumberOfRows();
			for (int i = 1; i < lastrownum; i++) {
				HashMap param = new HashMap();
				HSSFRow row = sheet.getRow(i);
				String v_xingming = "";
				String v_shenfengzheng = "";
				String v_orgno = "";
				if ("1".equals(etype)) {
					v_xingming = getRowCellValue(row.getCell(0));
					v_shenfengzheng = getRowCellValue(row.getCell(5));
					v_orgno = getRowCellValue(row.getCell(21));
				}
				if ("2".equals(etype)) {
					v_xingming = getRowCellValue(row.getCell(0));
					v_shenfengzheng = getRowCellValue(row.getCell(4));
					v_orgno = getRowCellValue(row.getCell(21));
				}
				if ("3".equals(etype)) {
					v_xingming = getRowCellValue(row.getCell(0));
					v_shenfengzheng = getRowCellValue(row.getCell(1));
					v_orgno = getRowCellValue(row.getCell(10));
				}
				boolean is = true;
				if (null == v_xingming || "".equals(v_xingming)) {
					is = false;
				}
				if (null == v_shenfengzheng || "".equals(v_shenfengzheng)) {
					is = false;
				}
				if (null == v_orgno || "".equals(v_orgno)) {
					is = false;
				} else {
					if (8 == v_orgno.length()) {
						String a = "select org.organization_id as oo from sys_t_organization org where org.parentorgid = '"
								+ v_orgno
								+ "' and org.orgname like '%集中供养五保户%'";
						param.put("executsql", a);
						List<HashMap> aa = extendsncDAO.queryAll(param);
						v_orgno = (String) aa.get(0).get("OO");
					}
					if (10 == v_orgno.length()) {
						is = true;
					} else {
						is = false;
					}
				}
				if (null == v_shenfengzheng || "".equals(v_shenfengzheng)) {
					is = false;
				} else {
					String a = "select fn_checkidcard('"
							+ v_shenfengzheng.trim().toUpperCase()
							+ "') as oo from dual";
					param.put("executsql", a);
					List<HashMap> aa = extendsncDAO.queryAll(param);
					String oo = aa.get(0).get("OO").toString();
					if ("0".equals(oo)) {
						is = false;
					} else {
						is = true;
					}

				}
				if (is) {
					String a = "select t.*  from wubaohu  t where upper(t.PAPERID) = upper('"
							+ v_shenfengzheng + "')";
					param.put("executsql", a);
					List<HashMap> aa = extendsncDAO.queryAll(param);
					if (null != aa && aa.size() > 0) {
						//更新已经存在人员信息，连接超时
						/*String memid = aa.get(0).get("MEMBER_ID").toString();
						String l = "update info_t_member mem set mem.degreesort='889' where mem.member_id='"
								+ memid + "'";
						param.put("executsql", l);
						extendsncDAO.updateAll(param);*/
					} else {
						sql = handleRow(etype, row, v_orgno);
						param.put("executsql", sql);
						extendsncDAO.insertAll(param);
					}
					
					 
				} else {
					unlist.add(row);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return unlist;
	}

	private String getRowCellValue(HSSFCell aCell) {
		String strCell = "";
		String str = "";
		if (null != aCell) {
			int cellType = aCell.getCellType();
			log.debug(cellType);
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC: // Numeric
				BigDecimal bd = new BigDecimal(aCell.getNumericCellValue());
				strCell = bd.toString();
				str += strCell + "";
				str = str.replace("?", "");
				return str;

			case HSSFCell.CELL_TYPE_STRING: // String
				strCell = aCell.getStringCellValue();
				str += strCell + "";
				str = str.replace("?", "");
				return str;

			case HSSFCell.CELL_TYPE_FORMULA: // formula
				strCell = String.valueOf(aCell.getNumericCellValue());
				strCell = strCell.replace("?", "a");
				str += strCell + "";
				str = str.replace("?", "");
				return str;

			case HSSFCell.CELL_TYPE_BLANK:// blank
				strCell = aCell.getStringCellValue();
				str += strCell + "";
				str = str.replace("?", "");
				return str;

			default:
				log.debug("格式读入不正确！");// 其它格式的数据
			}
		} else {
			return "";
		}
		return "";
	}

	private String handleRow(String etype, HSSFRow row, String orgno) {
		String sql = "";
		if ("1".equals(etype)) {
			String v_xingming = getRowCellValue(row.getCell(0));
			String v_wubaozhenghao = getRowCellValue(row.getCell(1));
			String v_xingbei = getRowCellValue(row.getCell(2));
			String v_mingzu = getRowCellValue(row.getCell(3));
			String v_shenfengzheng = getRowCellValue(row.getCell(5));
			String v_pizhunshijian = getRowCellValue(row.getCell(6));
			String v_gongyangbiaozhun = getRowCellValue(row.getCell(7));
			String v_renyuanleibie = getRowCellValue(row.getCell(8));
			String v_jiatingzhuzhi = getRowCellValue(row.getCell(9));
			String v_xiangzheng = getRowCellValue(row.getCell(19));
			String v_xianshi = getRowCellValue(row.getCell(20));
			String v_orgno = orgno;
			sql = "insert into fensan (xingbie, minzu, shengri, shenfenzheng, ruzhushijian, gongyangbiaozhun, renyuanleibie, familyno, orgno, jiatingzhuzhi, fangwujianshu, jianzhumianji, fangwujiegou, gengdimianji, gengzhongxingshi, nianchunshouru, tudishouru, yangzhiyeshouru, qitashouru, xiangzheng, xianshi, assist_type, xingming, wubaozhenghao, id)"
					+ " values ('"
					+ v_xingbei
					+ "', '"
					+ v_mingzu
					+ "',null, '"
					+ v_shenfengzheng
					+ "', '"
					+ v_pizhunshijian
					+ "', '"
					+ v_gongyangbiaozhun
					+ "', '"
					+ v_renyuanleibie
					+ "',genfamno('"
					+ v_orgno
					+ "') , '"
					+ v_orgno
					+ "', '"
					+ v_jiatingzhuzhi
					+ "', null, null, null,null,null, null, null, null,null, '"
					+ v_xiangzheng
					+ "', '"
					+ v_xianshi
					+ "', '00010', '"
					+ v_xingming
					+ "', '"
					+ v_wubaozhenghao
					+ "', hibernate_sequence.nextval)";

		}
		if ("2".equals(etype)) {
			String v_xingming = getRowCellValue(row.getCell(0));
			String v_shenfengzheng = getRowCellValue(row.getCell(4));
			String v_orgno = orgno;
			String v_wubaozhenghao = getRowCellValue(row.getCell(1));
			String v_xingbei = getRowCellValue(row.getCell(2));
			String v_mingzu = getRowCellValue(row.getCell(3));
			String v_pizhunshijian = getRowCellValue(row.getCell(6));
			String v_gongyangbiaozhun = getRowCellValue(row.getCell(7));
			String v_renyuanleibie = getRowCellValue(row.getCell(8));
			String v_jiatingzhuzhi = getRowCellValue(row.getCell(9));
			String v_xiangzheng = getRowCellValue(row.getCell(19));
			String v_xianshi = getRowCellValue(row.getCell(20));
			sql = "insert into jizhong (xingbei, mingzu, shenfengzheng, pizhunshijian, gongyangbiaozhun, renyuanleibie, "
					+ " xiangzheng, xianshi, familyno, orgno, assist_type, xingming, wubaozhenghao, id , jiatingzhuzhi) "
					+ "values ('"
					+ v_xingbei
					+ "','"
					+ v_mingzu
					+ "','"
					+ v_shenfengzheng
					+ "','"
					+ v_pizhunshijian
					+ "','"
					+ v_gongyangbiaozhun
					+ "',"
					+ "'"
					+ v_renyuanleibie
					+ "' ,'"
					+ v_xiangzheng
					+ "','"
					+ v_xianshi
					+ "',genfamno('"
					+ v_orgno
					+ "'),'"
					+ v_orgno
					+ "',"
					+ "'00010','"
					+ v_xingming
					+ "' ,'"
					+ v_wubaozhenghao
					+ "', hibernate_sequence.nextval ,'"
					+ v_jiatingzhuzhi + "')";
		}
		if ("3".equals(etype)) {
			String v_xingming = getRowCellValue(row.getCell(0));
			String v_shenfengzheng = getRowCellValue(row.getCell(1));
			String v_wubaozhenghao = getRowCellValue(row.getCell(2));
			String v_xingbei = getRowCellValue(row.getCell(3));
			String v_mingzu = getRowCellValue(row.getCell(5));
			String v_nianling = getRowCellValue(row.getCell(4));
			String v_jiatingzhuzhi = getRowCellValue(row.getCell(6));
			String v_xiangzheng = getRowCellValue(row.getCell(8));
			String v_xianshi = getRowCellValue(row.getCell(7));
			String v_orgno = orgno;

			sql = "insert into youfu (xingming, shenfengzheng, youfuzhenghao,"
					+ " xingbie, nianling, minzu,  xiangzhen, cun, "
					+ "jiatingzhuzhi , familyno, orgno, assist_type, id)"
					+ " values('"
					+ v_xingming
					+ "','"
					+ v_shenfengzheng
					+ "','"
					+ v_wubaozhenghao
					+ "','"
					+ v_xingbei
					+ "',"
					+ "'"
					+ v_nianling
					+ "','"
					+ v_mingzu
					+ "','"
					+ v_xiangzheng
					+ "','"
					+ v_xianshi
					+ "',"
					+ "'"
					+ v_jiatingzhuzhi
					+ "', genfamno('"
					+ v_orgno
					+ "'),'"
					+ v_orgno
					+ "','00001',hibernate_sequence.nextval)";

		}
		return sql;
	}

	private HSSFSheet getSheet(String realpath, int sheetnum)
			throws FileNotFoundException, IOException {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(realpath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(sheetnum);
		return sheet;
	}

	// 五保户导入部分
	public String getToolsmenu() {
		return pager.genToolsmenu();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MemberBaseinfoviewDTO edittypeinit(String memberId,String ds){
		MemberBaseinfoviewDTO e = new MemberBaseinfoviewDTO();
		HashMap params = new HashMap();
		String sql = ""; 
		sql = "select * from member_baseinfoview02 info " 
				+ " left join member_medicare med on info.member_id=med.member_id and info.ds=med.member_type " 
				+ " where info.member_id='" + memberId + "' and info.ds='" + ds + "'" ;
		params.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(params);
		for (HashMap s : rs) {
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSsn((String) s.get("SSN"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setFamilyid((String) s.get("FAMILYID"));
			String DS = (String) s.get("DS");
			e.setRpraddress((String) s.get("ORGNAME"));
			e.setDs(DS);
			String ASSIST_TYPE = (String) s.get("ASSIST_TYPE");
			e.setAssistType(ASSIST_TYPE);
			String ASSIST_TYPEX = (String) s.get("ASSIST_TYPEX");
			e.setAssistTypex(ASSIST_TYPEX);
			e.setMedicaretype((String) s.get("MEDICARE_TYPE"));
		}
		return e;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int editType(String memberId,String ds,String wubaohuFlag,String youfuFlag,String assistType,String sanwuFlag,String guerFlag,String assistTypex,String medicaretype){
		int return_i = 0;
		String sql = "";
		String sql_medical = "";
		HashMap params = new HashMap();
		try{	
			//医疗
			assistType = assistType.substring(0, 2) + sanwuFlag + wubaohuFlag + youfuFlag;
			assistTypex = guerFlag + assistTypex.substring(1, 6);
			sql_medical = "update member_baseinfoview02 info set info.a3 = '" + sanwuFlag + "', info.a4 = '" + wubaohuFlag + "',"
							+ " info.a5 = '" + youfuFlag + "',info.assist_type = '" + assistType + "', "
							+ " info.assist_typex = '" + assistTypex + "', info.a6 = '" + guerFlag + "', info.medicaretype = '" + medicaretype + "' "
							+ " where info.member_id = '" + memberId + "' and info.ds = '" + ds + "'";
			params.put("executsql", sql_medical);
			extendsDAO.updateAll(params);
			if("1".equals(ds)){
				//城市优抚
				String fmsafesort = "";
				if("1".equals(youfuFlag)){
					 fmsafesort = "复员军人";
				}else if ("0".equals(youfuFlag)){
					 fmsafesort = "非优抚对象";
				}
				//城市五保
				String fmtype = "";
				if("1".equals(wubaohuFlag)){
					fmtype = "五保";
				}else if ("0".equals(wubaohuFlag)){
					fmtype = "";
				}
				//城市三无
				String fmsort = "";
				if("1".equals(sanwuFlag)){
					fmsort = "三无";
				}else if ("0".equals(sanwuFlag)){
					fmsort = "其它";
				}
				//城市孤儿
				String fmtype5 = "";
				if("1".equals(guerFlag)){
					fmtype5 = "孤儿";
				}else if ("0".equals(guerFlag)){
					fmtype5 = "";
				}
				sql = "update familymember@cs fm set fm.fm_safesort = '"+ fmsafesort +"',fm.fm_type = '"+ fmtype +"',fm.fm_sort = '"+ fmsort +"',fm.fm_type5 = '" +fmtype5+ "',fm.medicaretype = '"+ medicaretype +"' where fm.fm_id = '" + memberId + "'";
			}else if("2".equals(ds)){
				//农村优抚
				String ftap = "";
				if("1".equals(youfuFlag)){
					ftap = "100";
				}else if("0".equals(youfuFlag)){
					ftap = "0";
				}
				//农村五保
				String degreesort = "";
				if("1".equals(wubaohuFlag)){
					degreesort = "889";
				}else if("0".equals(wubaohuFlag)){
					degreesort = "0";
				}
				//农村三无
				String degreesort1 = "";
				if("1".equals(sanwuFlag)){
					degreesort1 = "373";
				}else if("0".equals(sanwuFlag)){
					degreesort1 = "-1";
				}
				//农村孤儿
				String oldandinfirm = "";
				if("1".equals(guerFlag)){
					oldandinfirm = "841";
				}else if("0".equals(guerFlag)){
					oldandinfirm = "-1";
				}
				sql = "update info_t_member@nc im set im.ftap = '" + ftap + "',im.degreesort = '" + degreesort + "',im.degreesort1 = '" + degreesort1 + "',im.oldandinfirm = '" + oldandinfirm + "',im.medicaretype = '"+ medicaretype +"' where im.member_id = '" + memberId + "'";
			}
			params.put("executsql", sql);
			extendsDAO.updateAll(params);
			sql = "update WUBAOHU wbh set wbh.assist_type = '" + assistType + "',wbh.assist_typex = '" + assistTypex + "',wbh.medicaretype = '"+ medicaretype +"',wbh.utime = sysdate where wbh.member_id = '" + memberId +"' ";
			params.put("executsql", sql);
			extendsDAO.updateAll(params);
			return_i = 1;
		}catch (RuntimeException e) {
			return_i = 0;
			e.printStackTrace();
			throw e;
		}
		
		return return_i;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public void setExportxlsDAO(ExportxlsDAO exportxlsDAO) {
		this.exportxlsDAO = exportxlsDAO;
	}

	public ExportxlsDAO getExportxlsDAO() {
		return exportxlsDAO;
	}

	public void setExtendsncDAO(ExtendsncDAO extendsncDAO) {
		this.extendsncDAO = extendsncDAO;
	}

	public ExtendsncDAO getExtendsncDAO() {
		return extendsncDAO;
	}

}
