# Start service to test standalone 
mvn jetty:run

# Generate beans from schema
1. CD to project directory 
2. xjc schema/GfacMessage.xsd -p org.scigap.us3.message -d src/main/java/

## Command line testing 

curl -H "Accept:application/xml" -X POST -H "Content-Type:application/xml" -d "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Message><Header><experimentid>US3-ca0cfb0b-f556-8b64-7546-8d3763cd1737</experimentid><hostname>stampede.tacc.xsede.org</hostname><processorcount>32</processorcount><hostcount>2</hostcount><queuename>normal</queuename><walltime>600</walltime><userdn>/C=US/O=National Center for Supercomputing Applications/CN=Ultrascan3 Community User</userdn><cluster>stampede</cluster><us3_db>uslims3_cauma3d</us3_db><mgroupcount>1</mgroupcount><global_db>gfac2</global_db><global_host>uslims3.uthscsa.edu</global_host></Header><Body><Method>US3_Run</Method><input><parameters><name>inputData</name><value>file://home/us2airavata/us3airavata/input/hpcinputUS3-Experiment-f91cb59e-6369-45a4-a54d-f3721eee5abb.tar</value></parameters><parameters><name>jobxml</name><value>file://home/us2airavata/us3airavata/input/US3-Experiment-f91cb59e-6369-45a4-a54d-f3721eee5abb/jobxmlfile.xml</value></parameters></input></Body></Message>"  http://gridfarm005.ucs.indiana.edu:7071/ogce-rest/job/runjob/resubmitasync

# Response should return an experiment id

curl --request GET http://gf5.ucs.indiana.edu:7071/ogce-rest/job/jobstatus/US3-ca0cfb0b-f556-8b64-7546-8d3763cd1737

# Response should return job status and error incase of FAILED status 
