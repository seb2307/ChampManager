<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method='html' version='1.0' encoding='UTF-8' indent='yes'/>
<xsl:template match="/">
  <html>
  <body>
    <table border="3">
      <tr>
        <th align="left">Name</th>
        <th align="left">Surname</th>
        <th align="left">Weight</th>
        <th align="left">Age</th>
      </tr>
      <xsl:for-each select="Championship/competitor">
      <tr>
        <td><xsl:value-of select="Name"/></td>
        <td><xsl:value-of select="Surname"/></td>
        <td><xsl:value-of select="Weight"/></td>
        <td><xsl:value-of select="Age"/></td>
      </tr>
      </xsl:for-each>
    </table>    
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>