//package com.wanma.demoes.controller;
//
//import com.wanma.data.center.domain.OrderExtractData;
//import com.wanma.data.center.service.ChargingorderExtractService;
//import com.wanma.data.center.service.MoveData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.List;
//
///**
// * 登录
// */
//@RestController
//@RequestMapping("/login")
//public class LoginController {
//
//    @Autowired
//    MoveData moveData;
//
//    @Autowired
//    ChargingorderExtractService chargingorderExtractService;
//
//    @RequestMapping("/login")
//    public String sendCallLogRecording(){
//        System.out.println("-------222-345-------");
//
//        HashMap paramMap = new HashMap<>();
//        String getBeginChargeTime = "2016-01-18";//DateUtil.format(new Date(),DateUtil.TYPE_COM_YMD);//清除当天的数据
//        paramMap.clear();
//        moveData.getDbNameAndOrderExtractNameMap(paramMap,getBeginChargeTime);
//
//        paramMap.put("electricPileCode","3301021010000140");
//        paramMap.put("chargeDay", getBeginChargeTime);
//        List<OrderExtractData> selectChargingorderExtractList = chargingorderExtractService.selectChargingorderExtractList(paramMap);
//
//        System.out.println(selectChargingorderExtractList.size());
//        System.out.println("-------222-345---234----");
//        return "12344555555888888888888";
//    }
//
//}
