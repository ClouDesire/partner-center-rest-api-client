import jwt

secret = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCx3LXmZOx87DQv4LRBaFulah3EHKrDze6quPREbEQHKmJb6CyK4Fho2gNrkj3aSyGGaRLvwmovxbP7/11xaqAFPaQc6pioyj2uLFm6mnk6TU2325YE1I/GhJQ+S1Pu3yT14s3NUMaPav3mrXq2Ai5iK2VBd0WPxZxOxbD7HIr7tHu9NuROZvBdZUj9148oeej8Eal4otAPSKw0/gPqi0IuCxN8CQNwPtCHWapgAEVmoLk6+HTn/17Lahbtsa05J46asfd0I3lz7j8//axfQV/PogvK3KuNzzXLjqpXeChkmZKMESlCSVw8SPvGML7pxD9Rc4awIsi11/to5bLr03pn manuel@asd"

jwt.encode({
  "aud": "https: //login.microsoftonline.com/contoso.onmicrosoft.com/oauth2/token",
  "exp": 1484593341,
  "iss": "97e0a5b7-d745-40b6-94fe-5f77d35c6e05",
  "jti": "22b3bb26-e046-42df-9c96-65dbd72c1c81",
  "nbf": 1484592741,
  "sub": "97e0a5b7-d745-40b6-94fe-5f77d35c6e05"
}, secret, algorithm='RS256', headers={
  "alg": "RS256",
  "typ": "JWT",
  "x5t": "gx8tGysyjcRqKjFPnd7RFwvwZI0"
})

