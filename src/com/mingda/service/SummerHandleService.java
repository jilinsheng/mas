package com.mingda.service;

import java.util.List;

import com.mingda.dto.JizhongDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.model.Fensan;
import com.mingda.model.FensanExample;
import com.mingda.model.Jizhong;
import com.mingda.model.JizhongExample;
import com.mingda.model.Youfu;
import com.mingda.model.YoufuExample;

public interface SummerHandleService {
	public List<Jizhong> findJizhong(JizhongExample e, int curpage, String url);

	public List<Fensan> findFensan(FensanExample e, int curpage, String url);

	public List<Youfu> findYoufu(YoufuExample e, int curpage, String url);

	public JizhongDTO findJizhongById(String id);

	public WubaohuDTO findWubaohuById(String id,String ds);

	public JizhongDTO findYoufuById(String id);

	public List<WubaohuDTO> findGuarantee(String sql, int intValue,
			String string);

	public String getToolsmenu();

	public WubaohuDTO saveGuarantee(WubaohuDTO wubaohuDTO);

	public void removeGuarantee(JizhongDTO jizhongDTO);

	public List<JizhongDTO> findSpec(String sql, int intValue, String string);

	public void removeSpec(JizhongDTO jizhongDTO);

	//public JizhongDTO saveSpec(JizhongDTO jizhongDTO);
}
