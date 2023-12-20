# Router

## Purpose
This project contains the router building block that consumes a message from an incoming channel and routes the message to one or more target channels based on defined the routing rule.

Router building block contains one class-mediator, one template and two sequences.
They are:
* router-mediator(class-mediator)
* router-v1-template.xml
* router-v1-route-messages-sequence.xml
* router-v1-handle-fault-sequence.xml 

#### Supported Usecases

**1. Request - Reply pattern for single destination scenario**
	
i.e.  if the message should be routed to a single destination based on the routing rule, then response from the destination can be handled and send back to the client.
    Example: Sending a message to a destination, handling the response from destination and sending back to the client.

**2. Out only pattern for multiple destinations scenario** 
	
i.e. if the message should be routed to  multiple destinations based on the routing rule  and no responses are expected from the destinations.
    Example: Publish a message to multiple queues and topics.

## Running Locally  



#### To build the project

`./gradlew deployLocal`

#### To run local tests

`./gradlew cucumberAAT`

## Usage

Incoming message to the building block will be routed to outbound channels (sequences) based on the rule defined in a rule file. This rule file should be created and placed in registry.
When calling the building block in a mediation flow, registry location of this rule file should be passed as a input parameter to the building block. Optionally a custom error sequence can be passed to the building block to customize the error handling.

### Guidance for defining the Router Rule file

Routing rule should be included in a json file which can be stored in conf or gov space in the registry. Routing rule should have the following elements: active status, routing rule (xpathRule), conditions and outbound channels (sequences).
 
1. Rule file can have one or more rules, but there should be only one active rule
2. Active rule should have a valid xPath which will be applied against the payload
3. Active rule should contain conditions to match the result obtained from the xPath evaluation but no default condition supported
4. Conditions should have target sequences defined

#### Sample router rule file: 
(router-v1-sample-rule-definition.json)
```json
[{
    "active": true,
    "xpathRule": "//INVENTORY_RECEIPT_IFD/CTRL_SEG/INVENTORY_RECEIPT_IFD_SEG[1]/SITCOD/text()",
    "conditions": [
      {
        "condition": "9246",
        "sequences": [
          "router-v1-sample-channel1-sequence",
          "router-v1-sample-channel2-sequence",
          "router-v1-sample-channel3-sequence"
        ]
      },
      {
        "condition": "1096",
        "sequences": [
          "router-v1-sample-channel1-sequence",
          "router-v1-sample-channel4-sequence"	 
        ]
      }
    ]
  },
  ....
]
 ```

Calling the Router Building Block in the mediation flow:

This building block can be used in the mediation flow by calling the sequence template with arguments. The following sample shows template usage.

```xml
<call-template description="Call : router-v1-template" target="router-v1-template">
	<with-param name="routerRuleDefinitionPath" value="gov:/router-rules/router-v1-sample-rule-definition.json"/>
	<with-param name="routerOnErrorSequence" value="router-v1-sample-error-message-sequence"/>
	<with-param name="routerIntergationVersion" value="1.0.0"/>
	<with-param name="routerIntegrationReference" value="PIM1"/>
</call-template>
```

### Parameters

 - `routerRuleDefinitionPath`: *Required*
   - Path to the rule definition file placed in the registry space. If rule file placed in gov space, it should be passed as **gov:/**<file-path>. If it is in conf, it should be passed as **conf:/**<file-path> 
 - `routerOnErrorSequence`: *Optional*
   - Custom sequence to be executed on error. By default it will log and drop the message.
 - `routerIntergationVersion`: *Required*
   - The integration version of the mediation. e.g. 1.0.0. 
   - This value is used for  **logger-v3** loggs.If the  parameter is not supplied, **"intergationVersion"** of **logger-v3** will be null.
 - `routerIntegrationReference`: *Required*
   - The integration reference 0f the mediation. e.g. PIM1.
   - This value is used for  **logger-v3** loggs.If the  parameter is not supplied, **"intergationReference"** of **logger-v3** will be null.

#### Guidance for using input message

To execute the Router Building Block, request input message is required. The message should be matched with routing rule set to correctly determine where to send the data. 

Sample Payload:
```xml
<INVENTORY_RECEIPT_IFD>
	<CTRL_SEG>
		<TRNNAM>INV-RCV</TRNNAM>
		<TRNVER>2010.2</TRNVER>
		<INVENTORY_RECEIPT_IFD_SEG>
			<SEGNAM>INVENTORY_RECEIPT_IFD</SEGNAM>
			<SITCOD>9246</SITCOD>
			<TRANID>INV-RCV</TRANID>
			<TRNNUM>00000000000000000051</TRNNUM>
			<PONUM>55555</PONUM>
		</INVENTORY_RECEIPT_IFD_SEG>
		<INVENTORY_RECEIPT_IFD_SEG>
			<SEGNAM>TEST_RECEIPT_IFD</SEGNAM>
			<SITCOD>6789</SITCOD>
			<TRANID>TST-RCV</TRANID>
			<TRNNUM>00000000000000432789</TRNNUM>
			<PONUM>55555</PONUM>
		</INVENTORY_RECEIPT_IFD_SEG>
	</CTRL_SEG>
</INVENTORY_RECEIPT_IFD>
```

## Running sample test scenarios

#### Running sample Sequence scenario
   - Route the given payload to outbound channels.
  
**Used Artifacts:**
* router-v1-sample-api.xml
* router-v1-sample-api-response-sequence.xml
* router-v1-sample-error-message-sequence.xml
* router-v1-sample-channel1-sequence.xml
* router-v1-sample-channel2-sequence.xml
* router-v1-sample-channel3-sequence.xml

Here, router-v1-sample-rule-definition.json (routing-rule file) should be manually added to the following path gov:/router-rules/router-v1-sample-rule-definition.json in the gov space registry.

Sample curl command for sequence scenario is given below.
```
curl -k -X POST \
	https://localhost:8244/router/v1/usecase \
	-H 'content-type: application/xml' \
	-H 'on-error-sequence: router-v1-sample-error-message-sequence' \
	-H 'router-deff-path: gov:/router-rules/router-v1-sample-rule-definition.json' \
	-d '<INVENTORY_RECEIPT_IFD>
	<CTRL_SEG>
		<TRNNAM>INV-RCV</TRNNAM>
		<TRNVER>2010.2</TRNVER>
		<INVENTORY_RECEIPT_IFD_SEG>
			<SEGNAM>INVENTORY_RECEIPT_IFD</SEGNAM>
			<SITCOD>9246</SITCOD>
			<TRANID>INV-RCV</TRANID>
			<TRNNUM>00000000000000000051</TRNNUM>
			<PONUM>55555</PONUM>
		</INVENTORY_RECEIPT_IFD_SEG>
		<INVENTORY_RECEIPT_IFD_SEG>
			<SEGNAM>TEST_RECEIPT_IFD</SEGNAM>
			<SITCOD>6789</SITCOD>
			<TRANID>TST-RCV</TRANID>
			<TRNNUM>00000000000000432789</TRNNUM>
			<PONUM>55555</PONUM>
		</INVENTORY_RECEIPT_IFD_SEG>
	</CTRL_SEG>
</INVENTORY_RECEIPT_IFD>'
```

## Remarks
##### 1. Partial routing is considered as successful routing
Example: For a matching condition when there are multiple target sequences, even if it fails to send to one target sequence, this routing will be considered successful. It wil be considered as failure only if sendings to all the target sequences fails.

##### 2.Any exceptions from the target sequences will not be handled in the router building block 

##### 3.Namespaces are not supported in xPath
   
When defining xPath in rule file, it should not contain any namespace prefix. 
I.e xPath should be written to skip the namespace and match the element in the payload.

xPath expression supported by ESB in-built mediator (switch) will be supported by the router building block.

##### 4.Response aggregation from multiple destinations is not supported. Hence multiple destination routing should be used only for out only patterns

##### 5.Building block is using logger-v3

### Known issues :
xPath **count** function does not return integer and returns as a double value. ESB in-build mediator (switch) also has same behavior.
