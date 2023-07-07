package org.hejia.jrb.sms.receiver;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hejia.jrb.base.dto.SmsDTO;
import org.hejia.jrb.mq.constant.MQConst;
import org.hejia.jrb.sms.service.SmsService;
import org.hejia.jrb.sms.util.SmsProperties;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class SmsReceiver {

    private final SmsService smsService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MQConst.QUEUE_SMS_ITEM, durable = "true"),
            exchange = @Exchange(value = MQConst.EXCHANGE_TOPIC_SMS),
            key = { MQConst.ROUTING_SMS_ITEM }
    ))
    public void send(SmsDTO smsDTO) {
        log.info("SmsReceiver 消息监听");
        Map<String,Object> param = new HashMap<>();
        param.put("code", 1111);
        smsService.send(smsDTO.getMobile(), SmsProperties.TEMPLATE_CODE, param);
    }


}
