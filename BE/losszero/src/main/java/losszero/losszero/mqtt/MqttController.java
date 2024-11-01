package losszero.losszero.mqtt;

import com.google.gson.Gson;
import losszero.losszero.dto.realtime.RealtimeProductDTO;
import losszero.losszero.service.operation.OperationTimeService;
import losszero.losszero.service.realtime.RealtimeCircumstanceService;
import losszero.losszero.service.realtime.RealtimeProductService;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.MqttPahoComponent;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

@Controller
public class MqttController {
    @Autowired
    private OperationTimeService operationTimeService;

    @Autowired
    private RealtimeProductService realtimeProductService;

    @Autowired
    private RealtimeCircumstanceService realtimeCircumstanceService;

    @Bean
    @ServiceActivator(inputChannel = "realtimeInputChannel")
    public MessageHandler inboundMessageHandler() {
        Gson gson = new Gson();

        return message -> {
            String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
            String json = (String) message.getPayload();
            RealtimeProductDTO mqttDto = gson.fromJson(json, RealtimeProductDTO.class);
            System.out.println("!@$@!$%#%@^@" + mqttDto);

            switch (topic) {
                case "realtime-oper":
//                    operationTimeService.saveProductData(lineId, productData);
                    break;
                case "realtime-prod":
//                    realtimeProductService.saveProductData(mqttDto);
                    break;
                case "realtime-circ":
//                    realtimeCircumstanceService.;
                    break;
                default:
                    System.out.println("Unknown topic");
            }
        };
    }

    @EventListener
    public void connectLost(MqttConnectionFailedEvent failedEvent){
        MqttPahoComponent source = failedEvent.getSourceAsType();
        MqttConnectOptions options = source.getConnectionInfo();
        System.out.println("MQTT Connection is broken!!");
    }
}
