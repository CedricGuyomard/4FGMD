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
	synonyms_txt      = "";
	clinicalSigns_txt = "";
	command_txt       = "";
	disease_orphanum  = -1;

	for row in db.view('diseases/GetDiseasesByName'):
		if (row.key.upper() == disease.upper()):
			disease_orphanum = row.value['OrphaNumber'];
			if ( int(row.value['SynonymList']['count']) == 0 ):
				break;

			synonym_list = row.value['SynonymList']['Synonym'];
			for synonym in synonym_list:
				synonyms_txt += synonym['text'] + inner_separator;
			break;

	synonyms_txt = synonyms_txt[:-1] + type_separator;
	print("Synonymes :");
	print(synonyms_txt + '\n');

	for row in db.view('clinicalsigns/GetDiseaseClinicalSigns'):
		if (str(row.key) == "["+str(disease_orphanum)+", 'en']"):
			clinicalSigns_txt += row.value['clinicalSign']['Name']['text'] + inner_separator;	
	clinicalSigns_txt = clinicalSigns_txt[:-1] + type_separator;
	command_txt  = "SYNONYMS:"+ synonyms_txt + "CLINICALSIGNS:" + clinicalSigns_txt;
	print(command_txt);

def get_diseases_from_cs(db, clinical_sign):
	disease_txt = "";
	print(clinical_sign);

	for row in db.view('clinicalsigns/GetDiseaseByClinicalSign'):
		if((str(row.key).upper() == clinical_sign.upper()) or (clinical_sign.upper() in str(row.key).upper().split('/'))):
			disease_txt += row.value['disease']['Name']['text'] + inner_separator;	
	disease_txt = disease_txt[:-1] + type_separator;
	print("DISEASES: " + disease_txt);

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
