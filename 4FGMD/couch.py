#!/Library/Frameworks/Python.framework/Versions/3.3/bin/python3

import couchdb
import getopt
import sys
import os

inner_separator   = "$";
type_separator    = ";";

def usage():
	print("usage : " + sys.argv[0] + " options");
	print("options :");
	print("\t-h --help : display the help");
	print("\t-d --disease your_disease : display anything known on the disease");
	print("\t-c --clinicalsign your_cs : display any diseases matching the clinical sign");

def connect():
	couch = couchdb.Server('http://couchdb.telecomnancy.univ-lorraine.fr');
	couch.resource.credentials = ('rebaudo1u','CouchDB2A');
	return couch;

def selectDB(server, base):
	return server[base];

def get_disease_info(db, disease):
	is_init           = 0;
	synonyms_txt      = "empty";
	clinicalSigns_txt = "empty";
	command_txt       = "empty";
	disease_orphanum  = -1;
	disease_UMLS      = "empty";
	disease_OMIM      = "empty";

	for row in db.view('diseases/GetDiseasesByName'):
		if (row.key.upper() == disease.upper()):
			disease_orphanum = row.value['OrphaNumber'];
			if( int(row.value['ExternalReferenceList']['count']) > 0):
				for tab in row.value['ExternalReferenceList']['ExternalReference']:
					if ( tab['Source'] == "UMLS"):
						disease_UMLS  = tab['Reference'];
					if ( tab['Source'] == "OMIM"):
						disease_OMIM  = tab['Reference'];

			if ( int(row.value['SynonymList']['count']) == 0 ):
				break;

			for synonym in row.value['SynonymList']['Synonym']:
				if (is_init == 0):
					synonyms_txt = "";
					is_init = 1;
				synonyms_txt += synonym['text'] + inner_separator;
			break;
	is_init = 0;

	synonyms_txt = synonyms_txt[:] + type_separator;

	for row in db.view('clinicalsigns/GetDiseaseClinicalSigns'):
		if (str(row.key) == "["+str(disease_orphanum)+", 'en']"):
			if (is_init == 0):
				clinicalSigns_txt = "";
				is_init = 1;
			clinicalSigns_txt += row.value['clinicalSign']['Name']['text'] + inner_separator;	
	clinicalSigns_txt = clinicalSigns_txt[:] + type_separator;

	command_txt  = synonyms_txt + clinicalSigns_txt + disease_UMLS + type_separator + str(disease_OMIM);
	print(command_txt);

def get_diseases_from_cs(db, clinical_sign):
	is_init = 0;
	disease_txt = "empty";

	for row in db.view('clinicalsigns/GetDiseaseByClinicalSign'):
		if (is_init == 0):
			disease_txt = "";
			is_init = 1;
		if((str(row.key).upper() == clinical_sign.upper()) or (clinical_sign.upper() in str(row.key).upper().split('/'))):
			disease_txt += row.value['disease']['Name']['text'] + inner_separator;	
	disease_txt = disease_txt[:];
	print(disease_txt);

def main():
	disease           = "";
	clinical_sign     = "";

	if (len(sys.argv) < 2):
		usage();
		sys.exit(-1);

	try:
		opts, args = getopt.getopt(sys.argv[1:], "hd:c:",["help", "disease=", "clinicalsign="]);
	except getopt.GetoptError as err:
		print(str(err));
		usage();
		sys.exit(-2);

	for opt, arg in opts:
		if opt in ("-h", "--help"):
			usage();
			sys.exit(0);
		elif opt in ("-d", "--disease"):
			disease = arg;
		elif opt in ("-c", "--clinicalsign"):
			clinical_sign = arg;
		else:
			usage();
			sys.exit(-3);

	srv = connect();
	db  = selectDB(srv, 'orphadatabase');

	if (disease != ""):
		get_disease_info(db, disease);
	elif(clinical_sign != ""):
		get_diseases_from_cs(db, clinical_sign);

if __name__ == "__main__":
	main();
