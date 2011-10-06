<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
    <xsl:template match="record" mode="maps-xml">
    		setMySize(new google.maps.LatLng(<xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>,<xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>),new google.maps.LatLng(<xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>,<xsl:value-of select="cells/cell[4]/value" disable-output-escaping="yes"/>));	
    </xsl:template>
    <xsl:template match="record" mode="googlemap">
		placeMarker(new google.maps.LatLng( <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>,<xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>),"<xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>","<xsl:value-of select="cells/cell[4]/value" disable-output-escaping="yes"/>","<xsl:value-of select="cells/cell[5]/value" disable-output-escaping="yes"/>","<xsl:value-of select="cells/cell[6]/value" disable-output-escaping="yes"/>");
	</xsl:template>

	
	<xsl:template name="scriptBlock">
	  <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
				<script type="text/javascript"> 
				var stockholm = new google.maps.LatLng(59.32522, 18.07002);
				var map;
 
	  			function initialize() {
	  			
	   				var mapOptions = {
	      				zoom: 1,
	      				mapTypeId: google.maps.MapTypeId.ROADMAP,
	      				center: stockholm
	    			};
	 
	    			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
          			google.maps.event.addListener(map, 'click', function(event) {
   						<!-- placeMarker(event.latLng,"");-->
  					});
  					<xsl:apply-templates select="//data[@id='mapsTemp']//record" mode="maps-xml"/>
  					<xsl:apply-templates select="//data[@id='maps']//record" mode="googlemap"/>
  				}
  				
  				function placeMarker(location, about, myURL, myInfo, myImg) {
  					var marker = new google.maps.Marker({
      					position: location, 
      					map: map,
      					title: about
  					});
  					var message;
  					if (myImg == ""){
  						message = "<a href ="+ myURL+">"+about+"</a>"+myInfo;
  					}
  					else{
  						message = "<a href ="+ myURL+">"+about+"</a>"+"<br/>"+myInfo+"    "+" <img width=" + 50 + " src ="+myImg +"/>";
  					}
  					var infowindow = new google.maps.InfoWindow(
      					{ content: message,
       					 size: new google.maps.Size(50,50)
      				});
      				google.maps.event.addListener(marker, 'click', function() {
   					 infowindow.open(map,marker);
  					});
  					
    			}
    			function setMySize(lng1, lng2){
    				map.fitBounds(new google.maps.LatLngBounds(lng1,lng2));
    			}
 
			</script>
		</xsl:template>
    
</xsl:stylesheet>