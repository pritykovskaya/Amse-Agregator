<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="UTF-8"/>
<xsl:template match="/">
<html>
 <head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Language" content="ru"/>
    <title>AMSE Туризм</title>
    <link href="style.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
    	function deleteClicked(x) {
        	document.getElementById(x).submit();
        }
        
    </script>
  </head>
  <body>  	
  	<xsl:apply-templates select="//data[@id='forPrint']/collection/record" mode="print-xml"/>
  </body>
  </html>
</xsl:template>
 <xsl:template match="record" mode="print-xml">
 	<br/><FONT size="5"><b>Название достопримечательности : </b></FONT><xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>
    <xsl:if test="cells/cell[2]/value != ' '">
    	<p><b> Координаты: </b><xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>, <xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>
    	</p>
	</xsl:if>
    <xsl:if test="cells/cell[4]/value != ' '">
    	<p><b> INFO : </b><xsl:value-of select="cells/cell[4]/value" disable-output-escaping="yes"/>
        </p>
    </xsl:if>
    <form name="deleteForm" method="GET" action="forPrint.xml">
    <xsl:attribute name="id">
    	<xsl:value-of select="cells/cell[5]/value"/>
    </xsl:attribute>
    	<input type="hidden" name="id" value="{id}"/>
    	<input type="hidden" name="id_attr">
        	<xsl:attribute name="value">
            	<xsl:value-of select="cells/cell[5]/value"/>
            </xsl:attribute>
        </input>
    	<input type="hidden" name="type" value="{type}"/> 
        <input type="hidden" name="user">
        	<xsl:attribute name="value">
        		<xsl:value-of select="cells/cell[6]/value"/>
        	</xsl:attribute>
    	</input>
   	</form>
    <a class="buttonLink" >
    	<xsl:attribute name="onclick">deleteClicked('<xsl:value-of select="cells/cell[5]/value"/>')
        </xsl:attribute>
    Удалить из избранного</a>
 </xsl:template>
</xsl:stylesheet>