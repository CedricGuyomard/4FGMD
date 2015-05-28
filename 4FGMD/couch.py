#!/Library/Frameworks/Python.framework/Versions/3.3/bin/python3

import couchdb
import getopt
import sys
import os

inner_separator   = "$";
type_separator    = ";";

# Function called bad arguments are provided 
def usage():
	print("usage : " + sys.argv[0] + " options");
	print("options :");
	print("\t-h --help : display the help");
	print("\t-d --disease your_disease : display anything known on the disease");
	print("\t-c --clinicalsign your_cs : display any diseases matching the clinical sign");

# Connect to the database
def connect():
	couch = couchdb.Server('http://couchdb.telecomnancy.univ-lorraine.fr');
	couch.resource.credentials = ('rebaudo1u','CouchDB2A');
	return couch;

# Select the database
def selectDB(server, base):
	return server[base];

# Get the information from a disease
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
			# Get the orphanumber of the disease
			disease_orphanum = row.value['OrphaNumber'];

			# If there are some external referencies
			if( int(row.value['ExternalReferenceList']['count']) > 0):
				for tab in row.value['ExternalReferenceList']['ExternalReference']:
					if ( tab['Source'] == "UMLS"):
						# Get the CUI number
						disease_UMLS  = tab['Reference'];
					if ( tab['Source'] == "OMIM"):
						# Get the OMIM number
						disease_OMIM  = tab['Reference'];

			# If there is no synonym, we stop the loop
			if ( int(row.value['SynonymList']['count']) == 0 ):
				break;

			# Get all the synonym
			for synonym in row.value['SynonymList']['Synonym']:
				# Initialize the synonyms' line
				if (is_init == 0):
					synonyms_txt = "";
					is_init = 1;
				# Add all the synonym separated by a '$'
				synonyms_txt += synonym['text'] + inner_separator;
			break;
	is_init = 0;

	# Add a ';' at the end of synonyms list
	synonyms_txt = synonyms_txt[:] + type_separator;

	# Get clinical signs of the given disease
	for row in db.view('clinicalsigns/GetDiseaseClinicalSigns'):
		if (str(row.key) == "["+str(disease_orphanum)+", 'en']"):
			# Initialize the synonyms' line
			if (is_init == 0):
				clinicalSigns_txt = "";
				is_init = 1;
			# Add all the clinical signs separated by a '$'
			clinicalSigns_txt += row.value['clinicalSign']['Name']['text'] + inner_separator;	

	# Add a ';' at the end of clinical signs list
	clinicalSigns_txt = clinicalSigns_txt[:] + type_separator;

	# Create the commandline to get in java
	command_txt  = synonyms_txt + clinicalSigns_txt + disease_UMLS + type_separator + str(disease_OMIM);
	print(command_txt);

# Get diseases match the clinical sign
def get_diseases_from_cs(db, clinical_sign):
	is_init = 0;
	disease_txt = "empty";

	# Get diseases from the given clinical sign
	for row in db.view('clinicalsigns/GetDiseaseByClinicalSign'):
		# Initialize diseases' line
		if (is_init == 0):
			disease_txt = "";
			is_init = 1;

		# Check if the symptom is in CouchDB
		if((str(row.key).upper() == clinical_sign.upper()) or (clinical_sign.upper() in str(row.key).upper().split('/'))):
			disease_txt += row.value['disease']['Name']['text'] + inner_separator;

	# Create the commandline to get in java
	disease_txt = disease_txt[:];
	print(disease_txt);

def main():
	disease           = "";
	clinical_sign     = "";

	# Check the given arguments
	if (len(sys.argv) < 2):
		usage();
		sys.exit(-1);

	try:
		# Get the given arguments
		opts, args = getopt.getopt(sys.argv[1:], "hd:c:",["help", "disease=", "clinicalsign="]);
	except getopt.GetoptError as err:
		print(str(err));
		usage();
		sys.exit(-2);

	# Parse the arguments
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
