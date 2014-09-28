package com.mingda.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.ExtendsncDAO;
import com.mingda.dao.FensanDAO;
import com.mingda.dao.JizhongDAO;
import com.mingda.dao.WubaohuDAO;
import com.mingda.dao.YoufuDAO;
import com.mingda.dto.JizhongDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.model.Fensan;
import com.mingda.model.FensanExample;
import com.mingda.model.Jizhong;
import com.mingda.model.JizhongExample;
import com.mingda.model.Wubaohu;
import com.mingda.model.WubaohuExample;
import com.mingda.model.Youfu;
import com.mingda.model.YoufuExample;

public class SummerHandleServiceImpl implements SummerHandleService {

	private JizhongDAO jizhongDAO;
	private FensanDAO fensanDAO;
	private YoufuDAO youfuDAO;
	private WubaohuDAO wubaohuDAO;
	private ExtendsncDAO extendsncDAO;
	private ExtendsDAO extendsDAO;
	private Pager pager;

	public List<Jizhong> findJizhong(JizhongExample e, int curpage, String url) {
		return null;
	}

	public List<Fensan> findFensan(FensanExample e, int curpage, String url) {
		return null;
	}

	public List<Youfu> findYoufu(YoufuExample e, int curpage, String url) {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WubaohuDTO> findGuarantee(String sql, int currentpage,
			String url) {
		List<WubaohuDTO> list = new ArrayList<WubaohuDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			WubaohuDTO e = new WubaohuDTO(); 
			BigDecimal memberid = (BigDecimal) s.get("MEMBER_ID");
			String membername = (String ) s.get("MEMBERNAME");
			String paperid = (String ) s.get("PAPERID");
			String ssn = (String ) s.get("SSN");
			String familyid = (String ) s.get("FAMILYID");
			String familyno = (String ) s.get("FAMILYNO");
			String relmaster = (String ) s.get("RELMASTER");
			String sex = (String ) s.get("SEX");
			String mastername = (String ) s.get("MASTERNAME");
			String birthday = (String ) s.get("BIRTHDAY");
			String health = (String ) s.get("HEALTH");
			String sickentype = (String ) s.get("SICKENTYPE");
			String sickenname = (String ) s.get("SICKENNAME");
			String deformity = (String ) s.get("DEFORMITY");
			String defgrade = (String ) s.get("DEFGRADE");
			String rprkind = (String ) s.get("RPRKIND");
			String rprtype = (String ) s.get("RPRTYPE");
			String rpraddress = (String ) s.get("RPRADDRESS");
			String linkmode = (String ) s.get("LINKMODE");
			String address = (String ) s.get("ADDRESS");
			String personstate = (String ) s.get("PERSONSTATE");
			String salvationid = (String ) s.get("SALVATION_ID");
			String assisttype = (String ) s.get("ASSIST_TYPE");
			String ds = (String ) s.get("DS");
			String medicaretype = (String ) s.get("MEDICARETYPE");
			String gongyang = (String ) s.get("GONGYANG");
			String nation = (String ) s.get("NATION");
			String flag = (String ) s.get("FLAG");
			Date ctime = (Date ) s.get("CTIME");
			Date utime = (Date ) s.get("UTIME");
			String vapaperid = (String ) s.get("VA_PAPERID");
			String orgname = (String) s.get("ORGNAME");
			String assistTypex = (String) s.get("ASSIST_TYPEX");
			String type = (String) s.get("TYPE");
			
			e.setMemberId(memberid.toString());
			e.setMembername(membername);
			e.setPaperid(paperid);
			e.setSsn(ssn);
			e.setFamilyid(familyid);
			e.setFamilyno(familyno);
			e.setRelmaster(relmaster);
			e.setSex(sex);
			e.setMastername(mastername);
			e.setBirthday(birthday);
			e.setHealth(health);
			e.setSickentype(sickentype);
			e.setSickenname(sickenname);
			e.setDeformity(deformity);
			e.setDefgrade(defgrade);
			e.setRprkind(rprkind);
			e.setRprtype(rprtype);
			e.setRpraddress(rpraddress);
			e.setLinkmode(linkmode);
			e.setAddress(address);
			e.setPersonstate(personstate);
			e.setSalvationId(salvationid);
			e.setAssistType(assisttype);
			e.setDs(ds);
			e.setMedicaretype(medicaretype);
			e.setGongyang(gongyang);
			e.setNation(nation);
			e.setFlag(flag);
			e.setCtime(ctime);
			e.setUtime(utime);
			e.setVaPaperid(vapaperid);
			e.setOrgname(orgname);
			e.setAssistTypex(assistTypex);
			e.setType(type);

			list.add(e);
		}
		return list;
	}

	public JizhongDTO findJizhongById(String id) {
		JizhongDTO e = new JizhongDTO();
		JizhongExample example = new JizhongExample();
		example.createCriteria().andIdEqualTo(new BigDecimal(id));
		List<Jizhong> rs = jizhongDAO.selectByExample(example);
		if (0 != rs.size()) {
			Jizhong s = rs.get(0);
			e.setXingbei(s.getXingbei());
			e.setMingzu(s.getMingzu());
			e.setShenfengzheng(s.getShenfengzheng());
			e.setPizhunshijian(s.getPizhunshijian());
			e.setGongyangbiaozhun(s.getGongyangbiaozhun());
			e.setRenyuanleibie(s.getRenyuanleibie());
			e.setJiatingzhuzhi((String) s.getJiatingzhuzhi());
			e.setFangwujianshu(s.getFangwujianshu());
			e.setJianzhumianji(s.getJianzhumianji());
			e.setFangwujiegou(s.getFangwujiegou());
			e.setGengdimianji(s.getGengdimianji());
			e.setGengdixingshi(s.getGengdixingshi());
			e.setNianchunshouru(s.getNianchunshouru());
			e.setTudishouru(s.getTudishouru());
			e.setYangzhishouru(s.getYangzhishouru());
			e.setQitashouru(s.getQitashouru());
			e.setXiangzheng(s.getXiangzheng());
			e.setXianshi(s.getXianshi());
			e.setFamilyno(s.getFamilyno());
			e.setOrgno(s.getOrgno());
			if ("00000".equals(s.getAssistType())) {
				e.setAssistType("0");
			} else {
				e.setAssistType("1");
			}
			e.setShengri(s.getShengri());
			e.setWubaozhenghao(s.getWubaozhenghao());
			e.setId(s.getId());
			e.setDs("jizhong");
			e.setXingming(s.getXingming());
			e.setType("2");
		}
		return e;
	}

	public WubaohuDTO findWubaohuById(String id,String ds) {
		WubaohuDTO e = new WubaohuDTO();
		WubaohuExample example = new WubaohuExample();
		example.createCriteria().andMemberIdEqualTo(new BigDecimal(id))
								.andDsEqualTo(ds);
		List<Wubaohu> rs = wubaohuDAO.selectByExample(example);
		if (0 != rs.size()) {
			Wubaohu s = rs.get(0);
			e.setMemberId(s.getMemberId().toString());
			e.setMembername(s.getMembername());
			e.setNation(s.getNation());
			e.setPaperid(s.getPaperid().trim());
			e.setCtime(s.getCtime());
			e.setFamilyno(s.getFamilyno());
			e.setSsn(s.getSsn());
			e.setRelmaster(e.getRelmaster());
			e.setSex(s.getSex());
			e.setMastername(s.getMastername());
			e.setBirthday(s.getBirthday());
			e.setHealth(s.getHealth());
			e.setSickentype(s.getSickentype());
			e.setSickenname(s.getSickenname());
			e.setDeformity(s.getDeformity());
			e.setDefgrade(s.getDefgrade());
			e.setRprkind(s.getRprkind());
			e.setRprtype(s.getRprtype());
			e.setRpraddress(s.getRpraddress());
			e.setLinkmode(s.getLinkmode());
			e.setAddress(s.getAddress());
			e.setPersonstate(s.getPersonstate());
			e.setSalvationId(s.getSalvationId());
			e.setAssistType(s.getAssistType());
			e.setDs(s.getDs());
			e.setMedicaretype(s.getMedicaretype());
			e.setGongyang(s.getGongyang());
			e.setFlag(s.getFlag());
			e.setUtime(s.getUtime());
			e.setAssistTypex(s.getAssistTypex());
		}
		return e;
	}

	public JizhongDTO findYoufuById(String id) {
		JizhongDTO e = new JizhongDTO();
		YoufuExample example = new YoufuExample();
		example.createCriteria().andIdEqualTo(new BigDecimal(id));
		List<Youfu> rs = youfuDAO.selectByExample(example);
		if (0 != rs.size()) {
			Youfu s = rs.get(0);
			e.setXingbei(s.getXingbie());
			e.setMingzu(s.getMinzu());
			e.setShenfengzheng(s.getShenfengzheng());
			e.setJiatingzhuzhi((String) s.getJiatingzhuzhi());
			e.setFamilyno(s.getFamilyno());
			e.setOrgno(s.getOrgno());
			if ("00000".equals(s.getAssistType())) {
				e.setAssistType("0");// 不同意
			} else {
				e.setAssistType("1");// 同意
			}
			e.setWubaozhenghao(s.getYoufuzhenghao());
			e.setId(s.getId());
			e.setXingming(s.getXingming());
		}
		return e;
	}

	public String getToolsmenu() {
		return pager.genToolsmenu();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WubaohuDTO saveGuarantee(WubaohuDTO wubaohuDTO) {
		String v_membername = wubaohuDTO.getMembername();
		String v_birthday = wubaohuDTO.getBirthday();
		String v_paperid = wubaohuDTO.getPaperid();
		String v_sex = wubaohuDTO.getSex();
		String v_nation = wubaohuDTO.getNation();
		String v_health = wubaohuDTO.getHealth();
		String v_flag = wubaohuDTO.getFlag();
		String v_address = wubaohuDTO.getAddress();
		String v_familyno = wubaohuDTO.getFamilyno();
		String v_mastername = wubaohuDTO.getMastername();
		String v_personstate = "正常";
		String v_ds ="";
		if("wbh".equals(wubaohuDTO.getType())){
			v_ds = "2";
		}else if("sw".equals(wubaohuDTO.getType())){
			v_ds = "1";
		}
		String v_medicaretype = "";
		String v_gongyang = wubaohuDTO.getGongyang();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date vv_ctime = wubaohuDTO.getCtime();
		String v_ctime = sdf.format(vv_ctime);
		String v_orgno = wubaohuDTO.getOrgno();
		String v_ssn = wubaohuDTO.getSsn();
		String v_assist_type="";
		String v_assist_typex="";
		if(!"".equals(wubaohuDTO.getAssistType().trim())){
			v_assist_type = wubaohuDTO.getAssistType().trim();
		}
		String a1="0";
		String a2="0";
		String a3="0";
		String a4="0";
		String a5="0";
		if(!"".equals(v_assist_type)&& v_assist_type.length()==5){
			a1 = v_assist_type.substring(0, 1);
			a2 = v_assist_type.substring(1, 2);
			a3 = v_assist_type.substring(2, 3);
			a4 = v_assist_type.substring(3, 4);
			a5 = v_assist_type.substring(4, 5);
		}
		if("1".equals(v_gongyang)||"2".equals(v_gongyang)){
			if("1".equals(v_flag)){
				a4 = "1";
			}else if("0".equals(v_flag)){
				a4 = "0";
			}
		}else if("3".equals(v_gongyang)){
			if("1".equals(v_flag)){
				a5 = "1";
			}else if("0".equals(v_flag)){
				a5 = "0";
			}
		}
		v_assist_type = a1+a2+a3+a4+a5;
		
		if(!"".equals(wubaohuDTO.getAssistTypex().trim())){
			v_assist_typex = wubaohuDTO.getAssistTypex().trim();
		}
		String a6="0";
		String a7="0";
		String a8="0";
		String a9="0";
		String a10="0";
		String a11="0";
		if(!"".equals(v_assist_typex)&& v_assist_typex.length()==6){
			a6 = v_assist_typex.substring(0, 1);
			a7 = v_assist_typex.substring(1, 2);
			a8 = v_assist_typex.substring(2, 3);
			a9 = v_assist_typex.substring(3, 4);
			a10 = v_assist_typex.substring(4, 5);
			a11 = v_assist_typex.substring(5, 6);
		}
		v_assist_typex = a6+a7+a8+a9+a10+a11;
		String v_member_id = wubaohuDTO.getMemberId();
		
		String sql = "";
		String sql2 = "";
		if (null == v_member_id || "".equals(v_member_id)) {
			HashMap param1 = new HashMap();
			String aa = "select HIBERNATE_SEQUENCE.nextval@nc as aid from dual";
			param1.put("executsql", aa);
			v_member_id = extendsDAO.queryAll(param1).get(0).get("AID").toString();
			wubaohuDTO.setMemberId(v_member_id);
/*			param1 = new HashMap();
			aa = "select genfamnonc('" + v_orgno + "') as fno from dual";
			param1.put("executsql", aa);
			v_familyno = (String) extendsDAO.queryAll(param1).get(0).get("FNO");
			wubaohuDTO.setFamilyno(v_familyno);*/
			sql =  "     insert into wubaohu "
					 + "       (member_id, membername,paperid,ssn,familyid,familyno,sex,mastername,birthday,health,address,personstate,assist_type,ds,medicaretype,gongyang,nation,flag,ctime,utime,assist_typex)"
					 + "     values          "
					 + "       (             "
					 + " '" +  v_member_id + "',"
					 + " '" +  v_membername + "',"
					 + " '" +  v_paperid + "',"
					 + " '" +  v_ssn + "',"
					 + " '" +  v_member_id + "',"
					 + " genfamnonc('" + v_orgno + "') ,"
					 + " '" +  v_sex + "',"
					 + " '" +  v_mastername + "',"
					 + " '" +  v_birthday + "',"
					 + " '" +  v_health + "',"
					 + " '" +  v_address + "',"
					 + " '" +  v_personstate + "',"
					 + " '" +  v_assist_type + "',"
					 + " '" +  v_ds + "',"
					 + " '" +  v_medicaretype + "',"
					 + " '" +  v_gongyang + "',"
					 + " '" +  v_nation + "',"
					 + " '1',"
					 + " to_date('" +  v_ctime + "','yyyy-MM-dd hh24:ss:mi'),"
					 + " to_date('" +  v_ctime + "','yyyy-MM-dd hh24:ss:mi'),"
					 + " '" + v_assist_typex + "')";
			HashMap param = new HashMap();
			param.put("executsql", sql);
			this.extendsDAO.insertAll(param);
			HashMap param2 = new HashMap();
			String bb = "select wbh.familyno as no from wubaohu wbh where wbh.member_id='" + v_member_id + "'";
			param2.put("executsql", bb);
			v_familyno = extendsDAO.queryAll(param2).get(0).get("NO").toString();
			wubaohuDTO.setFamilyno(v_familyno);
			sql2 =   " insert into member_baseinfoview02  "
					 + "  (member_id, membername, paperid, ssn, familyid, familyno, sex, mastername, birthday, health, address, personstate, assist_type, ds, medicaretype, a1, a2, a3, a4, a5, area, nation, assist_typex, a6,a7,a8,a9,a10,a11)"
					 + "   values  "
					 + "  (   "
					 + " '" +     	v_member_id    + "', "
					 + " '" +     	v_membername    + "', "
					 + " '" +     	v_paperid    + "', "
					 + " '" +     	v_ssn    + "', "
					 + " '" +     	v_member_id    + "', "
					 + " '" +     	v_familyno    + "', "
					 + " '" +     	v_sex    + "', "
					 + " '" +     	v_mastername    + "', "
					 + " to_date('" +     	v_birthday    + "','yyyy/MM/dd'),"
					 + " '" +     	v_health    + "', "
					 + " '" +     	v_address    + "', "
					 + " '" +     	v_personstate    + "', "
					 + " '" +     	v_assist_type    + "', "
					 + " '" +     	v_ds    + "', "
					 + " '" +       v_medicaretype    + "', "
					 + " '" +     	a1    + "', "
					 + " '" +     	a2    + "', "
					 + " '" +     	a3    + "', "
					 + " '" +     	a4    + "', "
					 + " '" +     	a5    + "', "
					 + " substr('" +  v_familyno  + "',1,6), "
					 + " '" +     	v_nation  + "', "
					 + " '" +       v_assist_typex   + "', "
					 + " '" +       a6    + "', "
					 + " '" +       a7    + "', "
					 + " '" +       a8    + "', "
					 + " '" +       a9    + "', "
					 + " '" +       a10    + "', "
					 + " '" +       a11    + "') ";
			HashMap oparam = new HashMap();
			oparam.put("executsql", sql2);
			this.extendsDAO.insertAll(oparam);
		} else {
			sql = " update wubaohu "  
					+ "    set member_id    =   " + v_member_id + ",      " 
					+ "        membername   =  '" + v_membername + "',    " 
					+ "        paperid      =  '" + v_paperid + "',       " 
					+ "        ssn          =  '" + v_ssn + "',           " 
					+ "        sex          =  '" + v_sex + "',           " 
					+ "        birthday     =  '" + v_birthday + "',      " 
					+ "        health       =  '" + v_health + "',        " 
					+ "        address      =  '" + v_address + "',       " 
					+ "        nation       =  '" + v_nation + "',        " 
					+ "        flag         =  '1',          " 
					+ "        assist_type  =  '" + v_assist_type + "',   "
					+ "        utime        =  sysdate "
					+ "  where member_id =  '" + v_member_id + "'         ";    

			sql2 = "update member_baseinfoview02 " + " set  membername = '"
					+ v_membername + "', " + " paperid = '" + v_paperid
					+ "', " + " familyid = '" + v_member_id + "', "
					+ " ssn = '" + v_ssn + "', "
					+ " sex = '" + v_sex + "', "
					+ " birthday = to_date('" + v_birthday + "','yyyy-MM-dd'), "
					+ " health = '" + v_health + "', "
					+ " address = '" + v_address + "', " 
					+ " nation = '" + v_nation + "', " 
					+ " assist_type = '" + v_assist_type + "', "
					+ " a1 ='" + a1 + "' , " + " a2 = '" + a2 + "', "
					+ " a3 ='" + a3 + "', " + " a4 ='" + a4 + "', "
					+ " a5 ='" + a5 + "'" + " where member_id='" + v_member_id
					+ "'";
			
			HashMap param = new HashMap();
			param.put("executsql", sql);
			extendsDAO.updateAll(param);
			HashMap oparam = new HashMap();
			oparam.put("executsql", sql2);
			this.extendsDAO.updateAll(oparam);
		}
		return wubaohuDTO;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeGuarantee(JizhongDTO jizhongDTO) {
		String sql = "";
		if ("fensan".equals(jizhongDTO.getDs())) {
			sql = "delete fensan t where t.id='" + jizhongDTO.getId() + "'";
		} else if ("jizhong".equals(jizhongDTO.getDs())) {
			sql = "delete jizhong t where t.id='" + jizhongDTO.getId() + "'";
		}
		HashMap param = new HashMap();
		param.put("executsql", sql);
		extendsncDAO.updateAll(param);
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<JizhongDTO> findSpec(String sql, int intValue, String url) {
		List<JizhongDTO> list = new ArrayList<JizhongDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(intValue, extendsncDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsncDAO.queryRow(param);
		for (HashMap s : rs) {
			JizhongDTO e = new JizhongDTO();
			// XINGMING SHENFENGZHENG YOUFUZHENGHAO XINGBIE NIANLING MINZU
			// HUKOUSUOZAIDI XIANGZHEN
			// CUN JIATINGZHUZHI JIGOUBIANHAO FAMILYNO ORGNO ASSIST_TYPE ID
			// ORGNAME
			String xingbei = (String) s.get("XINGBIE");
			String mingzu = (String) s.get("MINZU");
			String shenfengzheng = (String) s.get("SHENFENGZHENG");
			String hukousuozaidi = (String) s.get("HUKOUSUOZAIDI");
			String xiangzheng = (String) s.get("XIANGZHENG");
			String xianshi = (String) s.get("XIANSHI");
			String familyno = (String) s.get("FAMILYNO");
			String orgno = (String) s.get("ORGNO");
			String assist_type = (String) s.get("ASSIST_TYPE");
			String xingming = (String) s.get("XINGMING");
			String wubaozhenghao = (String) s.get("YOUFUZHENGHAO");
			BigDecimal id = (BigDecimal) s.get("ID");
			String ds = (String) s.get("DS");
			String orgname = (String) s.get("ORGNAME");

			e.setXingbei(xingbei);
			e.setMingzu(mingzu);
			e.setShenfengzheng(shenfengzheng);
			e.setXiangzheng(xiangzheng);
			e.setXianshi(xianshi);
			e.setFamilyno(familyno);
			e.setOrgno(orgno);
			e.setAssistType(assist_type);
			e.setXingming(xingming);
			e.setWubaozhenghao(wubaozhenghao);
			e.setId(id);
			e.setDs(ds);
			e.setOrgno(orgname);
			e.setHukousuozaidi(hukousuozaidi);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void removeSpec(JizhongDTO jizhongDTO) {
		String sql = "";
		sql = "delete youfu  where id = '" + jizhongDTO.getId() + "'";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		extendsncDAO.updateAll(param);
	}

  /*@SuppressWarnings({ "rawtypes", "unchecked" })
	public JizhongDTO saveSpec(JizhongDTO jizhongDTO) {
		String v_xingbie = jizhongDTO.getXingbei();
		String v_minzu = jizhongDTO.getMingzu();
		String v_shenfenzheng = jizhongDTO.getShenfengzheng();
		String v_familyno = jizhongDTO.getFamilyno();
		String v_orgno = jizhongDTO.getOrgno();
		String v_jiatingzhuzhi = jizhongDTO.getJiatingzhuzhi();
		String v_xiangzheng = jizhongDTO.getXiangzheng();
		String v_assist_type = "";
		if ("1".equals(jizhongDTO.getAssistType())) {
			v_assist_type = "00001";
		} else if ("0".equals(jizhongDTO.getAssistType())) {
			v_assist_type = "00000";
		}
		String a1 = "";
		String a2 = "";
		String a3 = "";
		String a4 = "";
		String a5 = "";
		if ("1".equals(jizhongDTO.getAssistType())) {
			v_assist_type = "00001";
			a1 = "0";
			a2 = "0";
			a3 = "0";
			a4 = "0";
			a5 = "1";
		} else if ("0".equals(jizhongDTO.getAssistType())) {
			v_assist_type = "00000";
			a1 = "0";
			a2 = "0";
			a3 = "0";
			a4 = "0";
			a5 = "0";
		}
		String v_xingming = jizhongDTO.getXingming();
		String v_wubaozhenghao = jizhongDTO.getWubaozhenghao();
		BigDecimal v_id = jizhongDTO.getId();
		String sql = "";
		String sql2 = "";
		if (null == v_id) {
			HashMap param1 = new HashMap();
			String aa = "select   hibernate_sequence.nextval as aid from dual";
			param1.put("executsql", aa);
			v_id = (BigDecimal) extendsncDAO.queryAll(param1).get(0).get("AID");
			jizhongDTO.setId(v_id);
			param1 = new HashMap();
			aa = "select genfamno('" + v_orgno + "') as fno from dual";
			param1.put("executsql", aa);
			v_familyno = (String) extendsDAO.queryAll(param1).get(0).get("FNO");
			jizhongDTO.setFamilyno(v_familyno);
			sql = "insert into youfu (xingming, shenfengzheng, youfuzhenghao, xingbie, nianling, minzu, hukousuozaidi, xiangzhen, cun, jiatingzhuzhi, jigoubianhao, familyno, orgno, assist_type, id) "
					+ "values ('"
					+ v_xingming
					+ "', '"
					+ v_shenfenzheng
					+ "', '"
					+ v_wubaozhenghao
					+ "', '"
					+ v_xingbie
					+ "', null, '"
					+ v_minzu
					+ "', '"
					+ v_jiatingzhuzhi
					+ "', '"
					+ v_xiangzheng
					+ "', '"
					+ v_orgno
					+ "', '"
					+ v_jiatingzhuzhi
					+ "', '"
					+ v_familyno
					+ "', '"
					+ v_familyno
					+ "', '"
					+ v_orgno
					+ "', '"
					+ v_assist_type
					+ "', '" + v_id + "')";
			HashMap param = new HashMap();
			param.put("executsql", sql);
			this.extendsncDAO.insertAll(param);
			sql2 = "insert into member_baseinfoview02 "
					+ " (member_id, membername, paperid,  familyid, familyno,  mastername,   personstate, assist_type, ds,  a1, a2, a3, a4, a5, area) "
					+ " values ('"
					+ v_id
					+ "' ,'"
					+ v_xingming
					+ "' ,'"
					+ v_shenfenzheng
					+ "' ,'"
					+ v_id
					+ "','"
					+ v_familyno
					+ "','"
					+ v_xingming
					+ "','正常','"
					+ v_assist_type
					+ "','2','"
					+ a1
					+ "','"
					+ a2
					+ "','"
					+ a3
					+ "','"
					+ a4
					+ "','"
					+ a5
					+ "',substr ( '"
					+ v_familyno + "' ,1,6))";
			HashMap oparam = new HashMap();
			oparam.put("executsql", sql2);
			this.extendsDAO.insertAll(oparam);
		} else {
			sql = "update youfu " + " set xingming = '" + v_xingming + "', "
					+ " shenfengzheng = '" + v_shenfenzheng + "', "
					+ " youfuzhenghao = '" + v_wubaozhenghao + "', "
					+ " xingbie ='" + v_xingbie + "', " + " minzu = '"
					+ v_minzu + "', " + " hukousuozaidi = '" + v_jiatingzhuzhi
					+ "', " + " jiatingzhuzhi = '" + v_jiatingzhuzhi + "', "
					+ " assist_type = '" + v_assist_type + "' "
					+ " where id = '" + v_id + "'";
			HashMap param = new HashMap();
			param.put("executsql", sql);
			extendsncDAO.updateAll(param);
			sql2 = "update member_baseinfoview02 " + " set  membername = '"
					+ v_xingming + "', " + " paperid = '" + v_shenfenzheng
					+ "', " + " familyid = '" + v_id + "', "
					+ " assist_type = '" + v_assist_type + "',    " + " a1 ='"
					+ a1 + "' , " + " a2 = '" + a2 + "', " + " a3 ='" + a3
					+ "', " + " a4 ='" + a4 + "', " + " a5 ='" + a5 + "' "
					+ " where member_id='" + v_id + "'";
			HashMap oparam = new HashMap();
			oparam.put("executsql", sql2);
			this.extendsDAO.updateAll(oparam);
		}
		return jizhongDTO;
	}*/

	public WubaohuDAO getWubaohuDAO() {
		return wubaohuDAO;
	}

	public void setWubaohuDAO(WubaohuDAO wubaohuDAO) {
		this.wubaohuDAO = wubaohuDAO;
	}
	
	public JizhongDAO getJizhongDAO() {
		return jizhongDAO;
	}

	public void setJizhongDAO(JizhongDAO jizhongDAO) {
		this.jizhongDAO = jizhongDAO;
	}

	public FensanDAO getFensanDAO() {
		return fensanDAO;
	}

	public void setFensanDAO(FensanDAO fensanDAO) {
		this.fensanDAO = fensanDAO;
	}

	public YoufuDAO getYoufuDAO() {
		return youfuDAO;
	}

	public void setYoufuDAO(YoufuDAO youfuDAO) {
		this.youfuDAO = youfuDAO;
	}

	public ExtendsncDAO getExtendsncDAO() {
		return extendsncDAO;
	}

	public void setExtendsncDAO(ExtendsncDAO extendsncDAO) {
		this.extendsncDAO = extendsncDAO;
	}

}
