OUTPUT_DIR:="./target"
VERSION:="1.0"
TAG:="docker.io/kameshsampath/cars-web:$(VERSION)"
MVN_CMD:="./mvnw"
KUBE_DEPLOYMENT="./kube-files/cars-web.yaml"
KUBE_ISITO_DEPLOYMENT="./kube-files/istio-cars-web.yaml"
KEYCLOAK_URL:=$(shell minishift openshift service keycloak --url)
OPENSHIFT_IP=$(shell minishift ip)

.PHONY: build

clean:
	$(MVN_CMD) clean

build:
	$(MVN_CMD) clean -DskipTests -Dkeycloak.url=$(KEYCLOAK_URL) package

docker:	build
	docker build --tag $(TAG) --rm .
	docker tag $(TAG) docker.io/kameshsampath/cars-web

kube-inject:
	istioctl kube-inject -f $(KUBE_DEPLOYMENT) -o $(KUBE_ISITO_DEPLOYMENT)

deploy:
	oc apply -f $(KUBE_DEPLOYMENT)

undeploy:
	oc delete -f $(KUBE_DEPLOYMENT)

redeploy: undeploy deploy

rm-images:
	docker images | grep cars-web | awk '{n=$1":"$2; print "\x27" n "\x27"}' | xargs docker rmi