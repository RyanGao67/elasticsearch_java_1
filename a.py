import subprocess
import json
#curl -X GET -k https://elastic:changeme@ag18-cdf-single.ad.interset.com:31092/risk_scores\*/_search\?pretty
#curl -X POST  -k https://elastic:changeme@ag18-cdf-single.ad.interset.com:31092/risk_scores\*/_search\?pretty -d '{"query" : {"match_all" : {}}, "size" : 0}'
a = '{"query" : {"match_all" : {}}, "size" : 1000}'

result = subprocess.run(['curl', '-X', 'POST', '-k', '-H', 'Content-Type: application/json', 'https://elastic:changeme@ag18-cdf-single.ad.interset.com:31092/risk_scores*/_search', '-d', a], stdout=subprocess.PIPE)
a = result.stdout
a = str(a, 'utf-8')
 
a = json.loads(a)
a = a['hits']['hits']
 

a = map(lambda item: item['_source'], a)
a = list(a)

with open('data.txt', 'w') as outfile:
    json.dump(a, outfile)
print(a)

#from elasticsearch import Elasticsearch
#es = Elasticsearch([{'host': 'localhost', 'port': 9200}])

#for i in range(len(a)):
#    b = a[i]
#    es.index(index='risk_scores', body=b)

