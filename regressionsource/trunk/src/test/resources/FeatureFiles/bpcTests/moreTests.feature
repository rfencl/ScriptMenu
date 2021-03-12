Feature: Battery tests 

Scenario Outline: Charge battery 802 
  	When I open contactor for battery 802
    When I close contactor for battery 802 
    When I set inverter 103 power to <percentOfMaxPower>
	When I enable app "<appName>" 
	When I disable app "<appName>" 
	When I open contactor for battery 802
	
	
	
	Examples: 
		| percentOfMaxPower	| appName|
		|    19				|  PowerCommand|  
		
