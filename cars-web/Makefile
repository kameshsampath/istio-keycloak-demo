OUTPUT_DIR:="./target"
TAG:="kameshsampath/cars-web"
MVN_CMD:="./mvnw"
KUBE_DEPLOYMENT="./kube-files/cars-web.yaml"
KUBE_ISITO_DEPLOYMENT="./kube-files/istio-cars-web.yaml"
KEYCLOAK_PORT:=$(shell kubectl get svc keycloak -n istio-system -o=jsonpath='{.spec.ports[0].nodePort}')
OPENSHIFT_IP=$(shell minikube ip)

.PHONY: build

clean:
	$(MVN_CMD) clean
	rm -f $(KUBE_ISITO_DEPLOYMENT)

build:
	$(MVN_CMD) clean -DskipTests -Dopenshift.ip=$(OPENSHIFT_IP) -Dkeycloak.node.port=$(KEYCLOAK_PORT) package

docker:	build
	docker build --tag $(TAG) --rm .

kube-inject:
	istioctl kube-inject -f $(KUBE_DEPLOYMENT) -o $(KUBE_ISITO_DEPLOYMENT)

deploy:
	kubectl apply -f $(KUBE_DEPLOYMENT)

undeploy:
	kubectl delete -f $(KUBE_DEPLOYMENT)

redeploy: undeploy deploy