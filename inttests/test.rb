describe port(8080) do
  it { should be_listening }
  its('protocols') {should include 'tcp'}
end
describe http('http://localhost:8080/PolishedDemo') do
  its('status') { should cmp 200 }
end
describe http('http://localhost:8080/RestWebServices/atickets') do
  its('status') { should cmp 200 }
  its('headers.Content-Type') { should cmp 'application/json' }
end
describe http('http://localhost:8080/RestWebServices/btickets') do
  its('status') { should cmp 200 }
  its('headers.Content-Type') { should cmp 'application/json' }
end
describe http('http://localhost:8080/RestWebServices/ctickets') do
  its('status') { should cmp 200 }
  its('headers.Content-Type') { should cmp 'application/json' }
end
describe http('http://localhost:8080/SoapWebServices/ShiftWs?wsdl') do
  its('status') { should cmp 200 }
  its('headers.Content-Type') { should cmp 'text/xml;charset=UTF-8' }
end