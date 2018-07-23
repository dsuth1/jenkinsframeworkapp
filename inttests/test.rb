describe port(8080) do
  it { should be_listening }
  its('protocols') {should include 'tcp'}
end