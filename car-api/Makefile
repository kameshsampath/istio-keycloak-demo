OUTPUT_DIR:="./target"
VERSION:="1.0"
TAG:="docker.io/kameshsampath/cars-api:$(VERSION)"
MVN_CMD:="./mvnw"
KUBE_DEPLOYMENT="./kube-files/cars-api.yaml"
KUBE_ISITO_DEPLOYMENT="./kube-files/istio-cars-api.yaml"

.PHONY: build

clean:
	$(MVN_CMD) clean

build:
	$(MVN_CMD) clean -DskipTests package

docker:	build
	docker build --tag $(TAG) --rm .

kube-inject:
	istioctl kube-inject -f $(KUBE_DEPLOYMENT) -o $(KUBE_ISITO_DEPLOYMENT)

deploy:
	kubectl apply -f $(KUBE_ISITO_DEPLOYMENT)

undeploy:
	kubectl delete deployment cars-api

all: clean  build kube-inject deploy

rm-images:
	docker images | grep cars-web | awk '{n=$1":"$2; print "\x27" n "\x27"}' | xargs docker rmi