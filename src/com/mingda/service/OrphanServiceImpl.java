package com.mingda.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.GuerfileDAO;
import com.mingda.dao.WubaohuDAO;
import com.mingda.dto.GuerfileDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.model.Guerfile;
import com.mingda.model.GuerfileExample;
import com.mingda.model.Wubaohu;
import com.mingda.model.WubaohuExample;

public class OrphanServiceImpl implements OrphanService{
	static Logger log = Logger.getLogger(OrphanServiceImpl.class);
	
	private ExtendsDAO extendsDAO;
	private Pager pager;
	private GuerfileDAO guerfileDAO;
	private WubaohuDAO wubaohuDAO;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WubaohuDTO> findOrphan(String sql, int currentpage,
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
			String assisttypex = (String) s.get("ASSIST_TYPEX");
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
			e.setType(type);
			String dsval = "";
			String a1 = assisttype.substring(0, 1);
			String a2 = assisttype.substring(1, 2);
			String a3 = assisttype.substring(2, 3);
			String a4 = assisttype.substring(3, 4);
			String a5 = assisttype.substring(4, 5);
			String a6 = assisttypex.substring(0,1);
	/*		String a7 = assisttypex.substring(1,2);
			String a8 = assisttypex.substring(2,3);
			String a9 = assisttypex.substring(3,4);
			String a10 = assisttypex.substring(4,5);
			String a11 = assisttypex.substring(5,6);*/
			if ("1".equals(ds)) {
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
			} else if ("2".equals(ds)) {
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
			if("00000".equals(assisttype) && "000000".equals(assisttypex)){
				dsval = "普通居民";
			}
			e.setDsval(dsval);
			list.add(e);
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WubaohuDTO saveOrphan(WubaohuDTO wubaohuDTO) {
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
		String v_ds = wubaohuDTO.getDs();
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
		if("1".equals(v_flag)){
			a6 = "1";
		}else if("0".equals(v_flag)){
			a6 = "0";
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
			sql =  "     insert into WUBAOHU "
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
			String bb = "select gg.familyno as no from WUBAOHU gg where gg.member_id='" + v_member_id + "'";
			param2.put("executsql", bb);
			v_familyno = (String) extendsDAO.queryAll(param2).get(0).get("NO");
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
					 + " '" +       a11    + "') ";;
			HashMap oparam = new HashMap();
			oparam.put("executsql", sql2);
			this.extendsDAO.insertAll(oparam);
		} else {
			sql = " update WUBAOHU "  
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
					+ "        ds           =  '" + v_ds + "',            "
					+ "        utime        =  sysdate, " 
					+ "        assist_typex =  '" + v_assist_typex + "'  "
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
					+ " ds = '" + v_ds + "', "
					+ " assist_type = '" + v_assist_type + "', "
					+ " a1 ='" + a1 + "' , " + " a2 = '" + a2 + "', "
					+ " a3 ='" + a3 + "', " + " a4 ='" + a4 + "', "
					+ " a5 ='" + a5 + "',"
					+ " assist_typex = '" + v_assist_typex + "', "
					+ " a6 ='" + a6 + "' , " + " a7 = '" + a7 + "', "
					+ " a8 ='" + a8 + "', " + " a9 ='" + a9 + "', "
					+ " a10 ='" + a10 + "', " + " a11 ='" + a11 + "' "
					+ " where member_id='" + v_member_id
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

	@Override
	public void saveGuerfiles(List<GuerfileDTO> files){
		for (GuerfileDTO s : files) {
			Guerfile e = new Guerfile();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			guerfileDAO.insert(e);
		}
	}
	
	@Override
	public List<GuerfileDTO> findGuerfiles(String bizId) {
		List<GuerfileDTO> list = new ArrayList<GuerfileDTO>();
		GuerfileExample example = new GuerfileExample();
		example.createCriteria().andBizIdEqualTo(new BigDecimal(bizId));
		List<Guerfile> rs = guerfileDAO
				.selectByExample(example);
		for (Guerfile s : rs) {
			GuerfileDTO e = new GuerfileDTO();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			e.setFileId(s.getFileId());
			list.add(e);
		}
		return list;
	}
	
	public WubaohuDTO findGuerById(String id,String ds) {
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
	
	public void delGuerFile(String fid) {
		GuerfileExample example = new GuerfileExample();
		example.createCriteria().andFileIdEqualTo(new BigDecimal(fid));
		this.guerfileDAO.deleteByExample(example);
	}
	
	public String getToolsmenu() {
		return pager.genToolsmenu();
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public GuerfileDAO getGuerfileDAO() {
		return guerfileDAO;
	}

	public void setGuerfileDAO(GuerfileDAO guerfileDAO) {
		this.guerfileDAO = guerfileDAO;
	}

	public WubaohuDAO getWubaohuDAO() {
		return wubaohuDAO;
	}

	public void setWubaohuDAO(WubaohuDAO wubaohuDAO) {
		this.wubaohuDAO = wubaohuDAO;
	}

}
