package bss.service.cs;

import bss.model.cs.ContractRequired;

public interface ContractRequiredService {
	int deleteByPrimaryKey(String id);

    int insert(ContractRequired record);

    int insertSelective(ContractRequired record);

    ContractRequired selectConRequByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractRequired record);

    int updateByPrimaryKey(ContractRequired record);
}
