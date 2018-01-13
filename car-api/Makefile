OUTPUT_DIR:="./target"
TAG:="kameshsampath/cars-api"
MVN_CMD:="./mvnw"
KUBE_DEPLOYMENT="./kube-files/cars-api.yaml"
KUBE_ISITO_DEPLOYMENT="./kube-files/istio-cars-api.yaml"

.PHONY: build

clean:
	$(MVN_CMD) clean
	rm -f $(KUBE_ISITO_DEPLOYMENT)

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