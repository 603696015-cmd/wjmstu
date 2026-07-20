<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.io.FileReader"%>
<%@page import="org.xml.sax.InputSource"%>
<%!

/****************************************************************************
** 
** Function:  setUpInputSource()
** Input:   fileName - String
** Output:  is - InputSource
**
** Description:  This function returns the input source.
**
***************************************************************************/

private InputSource setUpInputSource(String fileName){
   InputSource is = new InputSource();
   is = setupFileSource(fileName);
   return is;
} 

/****************************************************************************
**
** Function:  setUpFileSource()
** Input:   fileName - String
** Output:  is - InputSource
**
** Description:  This function returns the input source.
**
***************************************************************************/
private InputSource setupFileSource(String filename){
   try{
      java.io.File xmlFile = new java.io.File( filename );
      if ( xmlFile.isFile() ){
         FileReader fr = new FileReader(xmlFile);
         InputSource is = new InputSource(fr);
         return is;
      }else{
    	  
      }    
   }catch(NullPointerException  npe){
      System.out.println( "Null pointer exception" + npe); 
      npe.printStackTrace();
   }catch(SecurityException se){
      System.out.println( "Security Exception" + se);
      se.printStackTrace();
   }catch(FileNotFoundException fnfe){
      System.out.println("File Not Found Exception" + fnfe);
      fnfe.printStackTrace();
   }
   return new InputSource();
}
%>