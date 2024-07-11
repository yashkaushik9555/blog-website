package com.example.Blog.website.Exception;
public interface APPConstant {
	//TODO pick Timezone, date format, and date time formate from application properties.
	public static final String TIMEZONE = "Asia/Kolkata";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	public static final String DATE_FORMAT_2 = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public static final String DATE_TIME_FORMAT_HM = "dd/MM/yyyy HH:mm";

	

	public static final String DATE_FORMAT_YEAR = "yyyy";

	
	public static final int LOGGED_IN_ACTION = 1;
	public static final int LOGGED_OUT_ACTION = 2;
	public static final int TIMED_OUT_ACTION = 3;
	
	public static final String APPROVAL = "Y";
	
	public static final String CONTENT_ENCODING = "UTF-8";
	public static final String  ORG_DOC_MST = "org_doc_mst";
	public static final String DATE_FORMAT_EXCEL = "dd-MM-yy";
	
	/*these variable declare for create common class for message and status send when we hit api's */
	/*success message*/
	public static final String STATUS_SUCCESS_MESSAGE="success";
	public static final String SUCCESS_MESSAGE="Data found successfully";
	/*fail message*/
	public static final String STATUS_FAIL_MESSAGE="fail";
	public static final String FAILED_MESSAGE="Data not found";
	
	public enum EBILL{
		VERSION("1.0.0501"),
		INVOICE("Invoice"),
		JOBWORK("Jobwork");
		
		private final String value;
		private EBILL(String val){
			this.value = val;
		}
		@Override
        public String toString() {
            return this.value;
        }
	}
	
}