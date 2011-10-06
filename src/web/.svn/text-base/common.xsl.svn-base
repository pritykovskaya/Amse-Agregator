<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes" encoding="UTF-8"/>
<xsl:include href="maps.xsl"/>
    <!--<xsl:key match="/page/items/item" name="item" use="@uid"/>-->

    <xsl:template match="/">

        <html>

            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
                <meta http-equiv="Content-Language" content="ru"/>
                <title>AMSE Туризм</title>
                <link href="style.css" type="text/css" rel="stylesheet"/>
                <script type="text/javascript">
                    function searchClicked() {
                        document.searchForm.submit();
                    }
                </script>
              <script type="text/javascript">
                  function addClicked() {
                        document.addForm.submit();
                 }
                 function printClicked() {
                        document.printForm.submit();
                  }
                    
              </script>
              <xsl:call-template name="scriptBlock"/>
               <!-- <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=true_or_false&amp;key=ABQIAAAAagSBU5_3ClK3meB5PAzQQxQaJtG5tQXIO35ApQMeCJS2-9685xRI2v9YCAn2DKbtVFr0yib1PjvK0w"
                        type="text/javascript"/>
                <script src="http://maps.google.com/maps?file=api&amp;v=2"
                        type="text/javascript"></script>
                <script type="text/javascript">
                    function initialize()
                    {

                    if (!GBrowserIsCompatible()){}else {
                    var map = new GMap2(document.getElementById("google-map"));
                    map.setCenter(new GLatLng(37.4419, -122.1419), 13);
                    }
                    }

                    downloadUrl("coords.xml", function(doc) {
                    alert("!!!");
                            var xmlDoc = xmlParse(document);
                            var markers = xmlDoc.documentElement.getElementsByTagName("marker");
                            for (var i = 0; i &lt; markers.length; i++) {
                              // obtain the attribues of each marker
                              var lat = parseFloat(markers[i].getAttribute("lat"));
                              var lng = parseFloat(markers[i].getAttribute("lng"));
                              var point = new google.maps.LatLng(lat,lng);
                              var html = markers[i].getAttribute("html");
                              var label = markers[i].getAttribute("label");
                              // create the marker
                              var marker = createMarker(point,label,html);
                            }
                            // put the assembled side_bar_html contents into the side_bar div
                            document.getElementById("side_bar").innerHTML = side_bar_html;
                          });


                </script>-->
            </head>
            <!--<body onload="initialize()" onunload="GUnload()">-->
            <body onload="initialize()">
                <table class="extTable">
                    <tr>
                        <td class="extTableTD">
                            <table class="header">
                                <tr>
                                    <td id="logo_p1" onclick="location.href='index.xml';">
                                    </td>
                                    <td colspan="2">
                                        <table id="searchContainer">
                                            <xsl:call-template name="find"/>
                                        </table>

                                    </td>
                                </tr>
                                <tr>
                                    <td id="logo_p2" onclick="location.href='index.xml';">
                                    </td>
                                    <td id="logo_p3" onclick="location.href='index.xml';">
                                    </td>
                                    <td id="linksContainer">
                                        <table id="linksTable" align="right">
                                            <tr>
                                                <td>
                                                    <a href="menu.xml">Все страны</a>
                                                </td>
                                                <!--<td>-->
                                                <!--<a href="#">Top10</a>-->
                                                <!--</td>-->
                                                <!--<td>-->
                                                <!--<a href="hotels.xml">Отели</a>-->
                                                <!--</td>-->
                                                <td>
                                                    <a href="largefind.xml">Расширенный поиск</a>
                                                </td>
                                            </tr>
                                            <!-- Добавить сюда вторую строку дял подчеркиваний ссылок. -->
                                        </table>
                                    </td>
                                </tr>
                            </table>

                            <table class="pageContainer">
                                <tr>
                                    <td id="leftColomn">
                                        <!-- Эту таблицу потмо разумно вынести в отдельный шаблон. !!!Популярные страны/города.!!! -->
                                        <xsl:call-template name="leftmenu"/>

                                    </td>
                                    <td id="centerColomn">
                                        <!-- Этот блок потмо разумно вынести в отдельный шаблон. !!!Города с картинками!!! -->
                                        <xsl:call-template name="main"/>

                                    </td>
                                    <td id="rightColomn">
                                        <xsl:call-template name="rightmenu"/>
                                    </td>
                                </tr>
                            </table>

                            <div class="klcopyright">
                                <a href="info.xml">Информация о создателях сайта</a>
                                <br/>
                                2010-2011
                            </div>

                        </td>
                    </tr>
                </table>
            </body>

        </html>


    </xsl:template>


    <xsl:template name="find">
        <!--<form action="attractions.xml" method="post">-->
        <tr>
            <td id="searchForm">
                <form id="searchForm" name="searchForm" method="GET" action="search.xml">
                    <input type="text" id="search" name="findTextBox"/>
                </form>
            </td>
            <td class="searchButtonTd">
                <a class="buttonLink" onclick="searchClicked()">Найти</a>
            </td>
        </tr>
        <!--</form>-->
    </xsl:template>


    <xsl:template name="countryTopBlock">
        <table class="simpleTop">
            <tr>
                <td class="simpleTopHeader">
                    Популярные страны:
                </td>
            </tr>
            <xsl:apply-templates select="page/data[@id ='countryTopBlock']/collection/record" mode="countryTopBlock"/>
        </table>
    </xsl:template>

    <xsl:template match="record" mode="countryTopBlock">
        <tr>
            <td class="simpleTopItemTitle">
                <a href="attractiondescription.xml?type=Country&amp;id={cells/cell[2]/value}">
                    <xsl:copy-of select="cells/cell[1]/value"/>
                </a>
            </td>
        </tr>
        <tr>
            <td class="simpleTopItemContent">
                <xsl:apply-templates select="cells/cell[3]/value/record" mode="simpleTopItemContent"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="record" mode="simpleTopItemContent">
        <a href="attractiondescription.xml?type=City&amp;id={cells/cell[2]/value}">
            <xsl:value-of select="cells/cell[1]/value"/>
        </a>
        <br/>
    </xsl:template>

    <xsl:template name="continentSelectBlock">
        <div class="simpleBlock">
            Выберите континент:
            <img usemap="#continentsMap" src="images/continents.jpg" alt="" border="0"/>
            <map name="continentsMap">
                <xsl:apply-templates select="page/data[@id = 'continentSelectBlock']/collection/record"
                                     mode="continentsMap"/>
            </map>
        </div>
    </xsl:template>

    <xsl:template match="record" mode="continentsMap">
        <area shape="poly" title="{cells/cell[1]/value}"
              href="attractiondescription.xml?id={cells/cell[2]/value}&amp;type=Continent">
            <xsl:attribute name="coords">
                <xsl:if test="cells/cell[1]/value = 'Австралия и Океания'">
                    148,108,177,110,189,91,171,79,147,88
                </xsl:if>
                <xsl:if test="cells/cell[1]/value = 'Северная Америка'">
                    15,28,86,21,41,67
                </xsl:if>
                <xsl:if test="cells/cell[1]/value = 'Южная Америка'">
                    47,67,73,81,57,113,43,80
                </xsl:if>
                <xsl:if test="cells/cell[1]/value = 'Европа'">
                    87,58,106,21,139,27,129,51,111,57
                </xsl:if>
                <xsl:if test="cells/cell[1]/value = 'Азия'">
                    138,36,181,42,169,72,132,70,127,53,138,52
                </xsl:if>
                <xsl:if test="cells/cell[1]/value = 'Африка'">
                    102,56,121,58,134,72,115,96,95,70
                </xsl:if>
            </xsl:attribute>
        </area>
    </xsl:template>


    <xsl:template name="attractionTopBlock">
        <table class="attractionsTop">
            <tr>
                <td class="attractionsTopHeader">
                    Популярные достопримечательности:
                </td>
            </tr>
            <xsl:apply-templates select="page/data[@id = 'attractionTopBlock']/collection/record"
                                 mode="attractionsTopHeader"/>
        </table>
    </xsl:template>

    <xsl:template match="record" mode="attractionsTopHeader">
        <tr>
            <td>
                <xsl:if test="position() mod 2 = 0">
                    <xsl:attribute name="class">
                        attractionsTopItemFirst
                    </xsl:attribute>
                </xsl:if>
                <xsl:if test="position() mod 2 != 0">
                    <xsl:attribute name="class">
                        attractionsTopItemSecond
                    </xsl:attribute>
                </xsl:if>
                <xsl:value-of select="cells/cell[1]/value" disable-output-escaping="yes"/>
                <br/>
                <div class="attractionsTopItemP1">
                    <a href="attractiondescription.xml?type=Attraction&amp;id={cells/cell[2]/value}">
                        <img width="66" height="55" src="{cells/cell[4]/value}"/>
                    </a>
                </div>
                <div class="attractionsTopItemP2">
                    <xsl:value-of select="cells/cell[3]/value" disable-output-escaping="yes"/>
                </div>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="loginBlock">
        <table width = "100%">
            <tr align="right">
                <td>
                    <xsl:if test="//AUTH = -1 or //AUTH = 0">
                        <form action="index.xml" method="POST">
                            Мыло:  <input type="text" name="email"/><br/>
                            Пароль: <input type="password" name="pwd"/><br/>
                            <input type="checkbox" name="toRegister" value="YES" />Зарегистрироваться.<br/>
                            <input type="submit" value="Поехали!" />
                        </form>
                    </xsl:if>

                    <xsl:if test="//AUTH =0">
                        Неверное мыло или пароль! Повторите попытку!
                    </xsl:if>

                    <xsl:if test="//AUTH =2">
                    	Вы зашли как : <xsl:value-of select="//email"/>
                        <form action="" method="POST">
                            <input type="hidden" name="logout" value="YES" />
                            <input type="submit" value="Выход" />
                        </form>
                    </xsl:if>

                    <xsl:if test="//AUTH =1">
                        Спасибо за регистрацию.
                    </xsl:if>

                </td>
            </tr>
        </table>
    </xsl:template>


    <xsl:template name="rankingBlock">
        <table width="80%" height="250" cellspacing="25" align="center" valign="middle">
            <tr align="center">
                <td>
                    <form action="" method="POST">
                        <select name="rankingValue">
                            <xsl:apply-templates select="page/data[@id = 'rankingBlock']/collection/record" mode="categoriesTop"/>
                        </select>
                        <br/>
                        <input type="submit" value="Ранжировать"/>
                    </form>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="record" mode="categoriesTop">
        <option>
            <xsl:value-of select="cells/cell[1]/value"/>
        </option>
    </xsl:template>


<!--	<xsl:template match="record" mode="googlemap">
		<p>HELLO WORLD!</p>
		<xsl:value-of select="cells/cell[2]/value" disable-output-escaping="yes"/>
		<div id="map_canvas" style="width: 350px; height: 250px;"/>
	</xsl:template>-->

    <!--
        <xsl:template name="leftmenulist">
            <table>
                <xsl:for-each select="//data[@id='leftMenu']//left-menu-item">
                    <xsl:sort order="ascending" select="name"/>

                    <tr>
                        <td class="simpleTopItemTitle">
                            <a class="left-menu-link">
                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="simpleTopItemContent">
                            <a class="left-menu-link">
                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>
                                <a class="left-menu-link">
                                    <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                                select="type"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="name"/>
                                </a>

                            </a>
                        </td>
                    </tr>


                    <br/>
                </xsl:for-each>
            </table>

        </xsl:template>
    -->
    <!--
        <xsl:template name="rightmenulist">
            <table class="attractionsTopItemFirst">
                <xsl:for-each select="//data[@id='rightMenu']//right-menu-item">

                    <tr>
                        <td colspan="2" class="attractionsTopItemP1">
                            <a class="right-menu-link">
                                <xsl:attribute name="href">attractiondescription.xml?id=<xsl:value-of select="id"/>&amp;type=<xsl:value-of
                                            select="type"/>
                                </xsl:attribute>
                                <xsl:value-of select="name"/>

                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="images/kolizey.jpg" alt="no image"/>
                        </td>
                        <td class="attractionsTopItemP2">
                            <xsl:value-of select="id"/>
                            sl djfslkjdlsj dlkjsldkfjs ldkjsl djflsdj
                        </td>
                    </tr>
                </xsl:for-each>
            </table>

        </xsl:template>
    -->
<xsl:template name="addBlock">
	
</xsl:template>
</xsl:stylesheet>
