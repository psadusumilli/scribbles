#!/bin/bash
 
#customer/site owner/service provider
domain=$1
key_password=$2
store_password=$3
commonname=$domain

#certificate authority
ca_cert=dep-ca-non-prod.crt
ca_key=dep-ca-non-prod.key
ca_seq_file=dep-ca-non-prod.seq
ca_password=XXXXXXXXXX

#company details
country=US
state=GA
locality=Fulton
organization=ThoughtWorks
organizationalunit=Software Engineering
email=cse.dep@vijayrc.com
 
printf "\n\ngenerating key request for $domain..\n\n."
openssl genrsa -des3 -passout pass:$key_password -out $domain.key 2048 -noout
printf "\n\nremoving passphrase from key"
openssl rsa -in $domain.key -passin pass:$key_password -out $domain.key
printf "\n\n-------consumer key--------"
cat $domain.key
 
printf "\n\ncreating csr..\n\n"
openssl req -new -key $domain.key -out $domain.csr -passin pass:$key_password -subj "/C=$country/ST=$state/L=$locality/O=$organization/OU=$organizationalunit/CN=$commonname/emailAddress=$email"
printf "-------consumer csr--------"
cat $domain.csr

printf "\n\ncreating customer certificate for CSR..\n\n."
openssl x509 -req -days 365 -in $domain.csr -CA $ca_cert -CAkey $ca_key -CAcreateserial -CAserial $ca_seq_file -out $domain.crt -passin pass:$ca_password
printf "---customer certificate----"
cat $domain.crt

printf "\n\nimporting into keystore...\n\n"
openssl pkcs12 -export -in $domain.crt -inkey $domain.key -certfile $domain.crt -name $domain -out $domain.p12 -password pass:$key_password
keytool -importkeystore -srckeystore $domain.p12 -srcstoretype pkcs12 -srcstorepass $key_password -destkeystore $domain.jks -deststoretype JKS -storepass $store_password -keypass $key_password -noprompt

printf "\n\n---consumer jks ready----\n\n"
keytool -list -v -keystore $domain.jks -alias $domain -storepass $store_password

tar -cf $domain.tar $domain.jks $domain.crt $domain.key dep-rio-non-prod-client.jks
printf "\n\narchived for consumer = $domain.tar"