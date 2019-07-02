
package com.wanma.demoes.repository;

import com.wanma.demoes.entity.TblChargingorderRecord;
import com.wanma.demoes.es001.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChargingorderRecordRepository extends ElasticsearchRepository<TblChargingorderRecord,String> {

    List<TblChargingorderRecord> findBypkChargingorder(String pkChargingorder);

}