package kr.co.ps119.service;

import kr.co.ps119.entity.Memo;

public interface MemoServiceAopMethod {

	Memo saveOneMemo(Memo memo);
	Memo updateOneMemo(Memo memo);

}
