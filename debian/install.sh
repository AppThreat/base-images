#! /usr/bin/env bash

curl -s "https://get.sdkman.io" | bash
chmod +x /root/.sdkman/bin/sdkman-init.sh
source $HOME/.sdkman/bin/sdkman-init.sh
echo -e "sdkman_auto_answer=true\nsdkman_selfupdate_feature=false\nsdkman_auto_env=true\nsdkman_curl_connect_timeout=20\nsdkman_curl_max_time=0" >> $HOME/.sdkman/etc/config
sdk install java ${JAVA_VERSION}
sdk install maven ${MAVEN_VERSION}
sdk offline enable
mv /root/.sdkman/candidates/* /opt/
rm -rf /root/.sdkman
python3 -m pip install --no-cache-dir --upgrade pip virtualenv
python3 -m pip install --no-cache-dir --upgrade --user pipenv poetry
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
chmod +x /root/.nvm/nvm.sh
source /root/.nvm/nvm.sh
nvm install ${NODE_VERSION}
