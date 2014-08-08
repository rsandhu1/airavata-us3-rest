package org.scigap.us3.client.util;

public class ServiceConstants {

	public static final String MYPROXY_SERVER = "myproxyServer";

	public static final String MYPROXY_PORT = "myproxyPort";

	public static final String MYPROXY_LIFETIME = "myproxy_lifetime";

	public static final String MYPROXY_USERNAME = "myproxyUserName";

	public static final String MYPROXY_PASSWD = "myproxyPasswd";

	public static final String TRUSTED_CERTS_FILE = "trustedCertsFile";

	public static final String HOSTCERTS_KEY_FILE = "hostcertsKeyFile";

	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static final String PROPERTY_JDBC_URL = "databaseUrl";

	public static final String GFAC_SERVICE_URL = "gfacserviceURL";

	public static final String GFAC_METHOD_NAME = "gfacmethodName";

	public static final String GFAC_LOCATION = "gfaclocation";

	public static final String USERDN = "userdn";

	public static final String MESSAGEBOX_URL = "messageBoxURL";

	public static final String BROKER_URL = "brokerURL";

	public static final String MESSAGE_TOPIC = "messagetopic";

	public static final String INPUTLOCATION = "inputLocation";

	public static final String OUTPUTLOCATION = "outputLocation";

	public static final String FILE_HOSTNAME = "fileHostName";

	public static final String NODE_CONFIGURATION = "hostNodeCountConfiguration";
	
	public static final String US3DBURL = "us3dburl";

	public static final String SUBMITTED = "SUBMITTED";

	public static final String FAILED = "FAILED";

	public static final String ERROR = "ERROR";
	
	public static final String SUCCESS = "SUCCESS";

	public static final String COMPLETED = "COMPLETED";
	
	public static final String SELECT_GRAMJOBID = "Select JOBID from NotificationDetails where ExperimentID = ?  and JOBID !='(null)'";

	public static final String SELECT_JOBSTATUS = "SELECT ExperimentStatus FROM  NotificationInfo where ExperimentID = ?";

	public static final String SELECT_JOBDETAILS = "SELECT ExperimentStatus, WorkingDirectory, OutputLocation, StdoutLocation, StderrLocation,US3DB,hostName FROM  NotificationInfo where ExperimentID = ?";

	public static final String SELECT_EXPERIMENTMESSAGE = "SELECT ExperimentMessage from NotificationDetails where ExperimentID =? and NotificationType ='LogInfo' and ExperimentMessage like '%Finished launching job%'";

	public static final String SELECT_EXPERIMENTINFO = "SELECT  WorkingDirectory, RequestXML, hostName, US3DB from NotificationInfo where ExperimentID =?";

	public static final String SELECT_ERRORMESSAGE = "SELECT ExperimentMessage from NotificationDetails where ExperimentID =? and NotificationType ='SendingFault'";

	public static final String SELECT_DESCRIPTION = "SELECT Experimentdescription FROM  NotificationInfo where ExperimentID = ?";

	public static final String INSERT_NOTIFICATION_INFO = "INSERT into NotificationInfo (ExperimentID,ExperimentStatus,RequestXML,hostName,US3DB) values (?,?,?,?,?)";

	public static final String UPDATE_NOTIFICATION_INFO = "UPDATE NotificationInfo set ExperimentStatus =? , Experimentdescription = ? where ExperimentID =?";
	
	public static final String UPDATE_NOTIFICATION_INFO_WITHLOCATION = "UPDATE NotificationInfo set ExperimentStatus =? , Experimentdescription = ? , OutputLocation = ? , StdoutLocation = ? , StderrLocation = ? where ExperimentID =?";

	public static String US3SELECTDATA = "Select * from analysis where gfacID = ?";
	
	public static String US3INSERTDATA = "INSERT INTO analysis (gfacID, stdout, stderr, tarfile, status, cluster, us3_db) VALUES (?,?,?,?,?,?,?) ";
	
	public static String US3UPDATEDATA = "UPDATE analysis set stdout =? , stderr = ?, tarfile = ?, status = ? where gfacID = ?";
	
	public static enum ExperimentStatus {INITIALIZED, ACTIVE, COMPLETED, FINISHED, FAILED,PENDING,DONE,SUSPENDED, UNSUBMITTED, SUBMITTED,CANCELED};
	
    public static final String SELECT_NOTIFICATION_INFO = "Select NotificationType, WorkingDirectory, OutputLocation, StdoutLocation, StderrLocation,ExperimentStatus from NotificationInfo where ExperimentID = ? ";
    
    public static final String ADD_NOTIFICATION_INFO = "insert into NotificationInfo (ExperimentID,UserDN,NotificationType,Experimentmodified,ExperimentStatus, Experimentdescription) values (?,?,?,?,?,?)";
  
    public static final String UPTATE_NOTIFICATION_INFO = "Update NotificationInfo set NotificationType = ?,  Experimentmodified = ?, ExperimentStatus = ?, ExperimentDuration=?, Experimentdescription = ?, UserDN = ?, WorkingDirectory = ? , OutputLocation = ? , StdoutLocation = ? , StderrLocation = ?  where ExperimentID = ?";
    
    public static final String ADD_NOTIFICATION_DETAILS = "insert into NotificationDetails (ExperimentID, NotificationType,  ExperimentMessage, ServiceLocation, ComputeLocation, Experimentinput, Experimentoutput, JOBID ) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATEDATA = "UPDATE analysis set status = ?, queue_msg= ? where gfacID = ?";

}
