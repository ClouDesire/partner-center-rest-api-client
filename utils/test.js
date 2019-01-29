var shaSig = "E7:9F:A1:ED:35:C6:4A:93:8C:A2:2A:3E:A4:14:65:1F:37:D2:DD:96";
var sigOctets = shaSig.split(":");
var sigBuffer = Buffer.alloc(sigOctets.length)
for(var i=0; i<sigOctets.length; i++){
   sigBuffer.writeUInt8(parseInt(sigOctets[i], 16), i);
}
//Perform base64url-encoding as per RFC7515 Appendix C
var x5t = sigBuffer.toString('base64').replace('=', '').replace('+', '-').replace('/', '_');

console.log(x5t);
