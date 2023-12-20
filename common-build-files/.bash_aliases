alias docker.clean='docker ps -a -q -f status=exited |xargs -r docker rm -v && docker images -f "dangling=true" -q|xargs -r docker rmi'

alias docker.mysql='docker run --name wso2-mysql --net wso2_nw -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:5.6'
alias docker.mysql.shell='docker run -it --net wso2_nw --rm mysql:5.6 sh -c "exec mysql -hwso2-mysql -P3306 -uroot -proot"'

alias docker.am='docker pull 10.228.3.55:443/wso2/am-1.9.1; docker run -it --name am-1.9.1 -p 9446:9446 -p 9766:9766 -p 10400:10400 -p 8283:8283 -p 8246:8246 -p 7711:7711 --net wso2_nw -v /home/docker/am-1.9.1/data:/opt/wso2am-1.9.1/repository --rm 10.228.3.55:443/wso2/am-1.9.1'
alias docker.am.shell='docker exec -it am-1.9.1 bash'

alias docker.das='docker pull 10.228.3.55:443/wso2/das-3.0.0; docker run -it --name das-3.0.0 -p 9445:9445 -p 9765:9765 -p 7713:7713 -p 8245:8245 -p 8282:8282 -p 9162:9162 --net wso2_nw -v /home/docker/das-3.0.0/data:/opt/wso2das-3.0.0/repository --rm 10.228.3.55:443/wso2/das-3.0.0'
alias docker.das.shell='docker exec -it das-3.0.0 bash'

alias docker.dss='docker pull 10.228.3.55:443/wso2/dss-3.5.1; docker run -it --name dss-3.5.1 -p 9448:9448 -p 9768:9768 --net wso2_nw -v /home/docker/dss-3.5.1/data:/opt/wso2dss-3.5.1/repository --rm 10.228.3.55:443/wso2/dss-3.5.1'
alias docker.dss.shell='docker exec -it dss-3.5.1 bash'

alias docker.esb='docker pull 10.228.3.55:443/wso2/esb-4.9.0; docker run -it --name esb-4.9.0 -p 9444:9444 -p 9764:9764 -p 8244:8244 -p 8281:8281 --net wso2_nw -v /home/docker/esb-4.9.0/data:/opt/wso2esb-4.9.0/repository --rm 10.228.3.55:443/wso2/esb-4.9.0'
alias docker.esb.shell='docker exec -it esb-4.9.0 bash'

alias docker.greg='docker pull 10.228.3.55:443/wso2/greg-5.0.0; docker run -it --name greg-5.0.0 -p 9449:9449 -p 9769:9769 --net wso2_nw -v /home/docker/greg-5.0.0/data:/opt/wso2greg-5.0.0/repository --rm 10.228.3.55:443/wso2/greg-5.0.0'
alias docker.greg.shell='docker exec -it greg-5.0.0 bash'
alias docker.greg.setup='docker run -it --net=wso2_nw --rm 10.228.3.55:443/wso2/greg-5.0.0 sh -c bash'

alias docker.greg5.1='docker pull 10.228.3.55:443/wso2/greg-5.1.0; docker run -it --name greg-5.1.0 -p 9449:9449 -p 9769:9769 --net wso2_nw -v /home/docker/greg-5.1.0/data:/opt/wso2greg-5.1.0/repository --rm 10.228.3.55:443/wso2/greg-5.1.0'
alias docker.greg5.1.shell='docker exec -it greg-5.1.0 bash'

alias docker.is='docker pull 10.228.3.55:443/wso2/is-5.0.0; docker run -it --name is-5.0.0 -p 9443:9443 -p 9763:9763 -p 8000:8000 -p 10500:10500 --net wso2_nw -v /home/docker/is-5.0.0/data:/opt/wso2is-5.0.0/repository --rm 10.228.3.55:443/wso2/is-5.0.0'
alias docker.is.shell='docker exec -it is-5.0.0 bash'

alias docker.mb='docker pull 10.228.3.55:443/wso2/mb-3.2.0; docker run -it --name mb-3.2.0 -p 9447:9447 -p 9767:9767 -p 5676:5676 -p 8676:8676 -p 1887:1887 -p 8837:8837 -p 7615:7615 --net wso2_nw -v /home/docker/mb-3.2.0/data:/opt/wso2mb-3.2.0/repository --rm 10.228.3.55:443/wso2/mb-3.2.0'
alias docker.mb.shell='docker exec -it mb-3.2.0 bash'

alias docker.smtp='docker run -d -p 2525:25 --net wso2_nw --name fake_smtp -v /tmp/fakemail:/var/mail munkyboy/fakesmtp'
alias docker.smtp.shell='docker exec -it fake_smtp bash'

alias docker.xref='docker run -d --name fake_xref -p 9010:8080 10.228.3.55:443/engineer/fake-xref'
alias docker.xref.shell='docker exec -it fake_xref bash'
