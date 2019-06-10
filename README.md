# riff-boot-java-decode


## Install

    riff system install --node-port

    riff namespace init function-chain --dockerhub $USER


## Deploy


    riff function create message-decoder -n function-chain \
      --git-repo https://github.com/danupo068/riff-boot-java-decode-demo.git \
      --image $USER/riff-boot-java-decoder:v1 \
      --verbose


# Verify

    riff service invoke message-decoder -n function-chain --text -- -w '\n' \
       -d "SGVsbG8gZXZlcnkgb25lLCBIb3BlIHlvdSBoYWQgZ3JlYXQgdGltZSBhdCBDRiBTdW1taXQgMjAxOQ=="

    riff service invoke message-decoder  --text -- -w '\n' \
       -d "SGVsbG8gRXZlcnlvbmUgQCBDRiBTdW1taXQgMjAxOSwgSGF2aW5nIGEgZ3JlYXQgdGltZSA6KQ=="

    riff service invoke message-encoder -n function-chain  --text -- -w '\n' \
       -d "Hello every one, Hope you had great time at CF Summit 2019"

# Function Chain

    riff channel create plainmessages --cluster-bus stub -n function-chain

    riff channel create encodedmessages --cluster-bus stub -n function-chain

    riff subscription create --channel plainmessages --subscriber message-encoder --reply-to encodedmessages -n function-chain

    riff subscription create --channel encodedmessages --subscriber message-decoder -n function-chain


# Post messsages from a pod within the cluster

    kubectl run -it --image=tutum/curl client --restart=Never -n function-chain \
    --overrides='{"apiVersion": "v1", "metadata":{"annotations": {"sidecar.istio.io/inject": "true"}}}'

    curl -v plain-messages -d "Hello Everyone at CF Summit 2019; Thanks for watching the demo"


# Check messages at the receiving side(decoder deployment)


    kail run -d message-decoder -n function-chain

    kubectl logs decoder-00001-deployment-77d75cc69c-gp7vf -n function-chain -c user-container --tail=10 -f


Based on the official Knative documentation, licensed under the Creative Commons Attribution 4.0 License,
and code samples are licensed under the Apache 2.0 License