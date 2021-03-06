***compatible with CDH 5.16.2***

# Steps to install

```bash
mvn -DskipTests package -Pdist
```

## before install 

### for solr
```bash
solrctl instancedir --generate $HOME/solr_configs
solrctl instancedir --list

solrctl instancedir --create vertex_index $HOME/solr_configs
solrctl instancedir --create edge_index $HOME/solr_configs
solrctl instancedir --create fulltext_index $HOME/solr_configs
```

## install Atlas
```bash
tar -xzvf apache-atlas-${project.version}-server.tar.gz
cd atlas-${project.version}
update conf/atlas-application.properties
./bin/atlas_start.py http://twdp-dn1:21000
./bin/quick_start.py http://twdp-dn1:21000
```

## login
http://twdp-dn1:21000
username/password: admin/admin

## setup Hive Hook
Atlas Hive hook registers with Hive to listen for create/update/delete operations and updates the metadata in Atlas, via Kafka notifications, for the changes in Hive. Follow the instructions below to setup Atlas hook in Hive:

Set-up Atlas hook in hive-site.xml by adding the following:
```xml
    <property>
      <name>hive.exec.post.hooks</name>
      <value>org.apache.atlas.hive.hook.HiveHook</value>
    </property>
```
untar apache-atlas-${project.version}-hive-hook.tar.gz
cd apache-atlas-hive-hook-${project.version}
Copy entire contents of folder `apache-atlas-hive-hook-${project.version}/hook/hive` to `/opt/local/hive/lib`
Add `export HIVE_AUX_JARS_PATH=/opt/local/hive/lib/atlas-hive-plugin-impl` in hive-env.sh of your hive configuration
Copy `atlas-application.properties` to `/opt/local/hive/lib/`.
Add jvm options: `-Datlas.conf=/opt/local/hive/lib/`

```bash
hook-bin/import-hive.sh
```


# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

Apache Atlas Overview
=====================

Apache Atlas framework is an extensible set of core
foundational governance services – enabling enterprises to effectively and
efficiently meet their compliance requirements within Hadoop and allows
integration with the whole enterprise data ecosystem.

This will provide true visibility in Hadoop by using both a prescriptive
and forensic model, along with technical and operational audit as well as
lineage enriched by business taxonomical metadata.  It also enables any
metadata consumer to work inter-operably without discrete interfaces to
each other -- the metadata store is common.

The metadata veracity is maintained by leveraging Apache Ranger to prevent
non-authorized access paths to data at runtime.
Security is both role based (RBAC) and attribute based (ABAC).


Build Process
=============

1. Get Atlas sources to your local directory, for example with following commands
   $ cd <your-local-directory>
   $ git clone https://github.com/apache/atlas.git
   $ cd atlas

   # Checkout the branch or tag you would like to build
   #
   # to checkout a branch
     $ git checkout <branch>

   # to checkout a tag
     $ git checkout tags/<tag>

2. Execute the following commands to build Apache Atlas

   $ export MAVEN_OPTS="-Xms2g -Xmx2g"
   $ mvn clean install
   $ mvn clean package -Pdist

3. After above build commands successfully complete, you should see the following files

   distro/target/apache-atlas-1.0.0-bin.tar.gz
   distro/target/apache-atlas-1.0.0-hbase-hook.tar.gz
   distro/target/apache-atlas-1.0.0-hive-hook.tar.gz
   distro/target/apache-atlas-1.0.0-kafka-hook.tar.gz
   distro/target/apache-atlas-1.0.0-sources.tar.gz
   distro/target/apache-atlas-1.0.0-sqoop-hook.tar.gz
   distro/target/apache-atlas-1.0.0-storm-hook.tar.gz

4. For more details on building and running Apache Atlas, please refer to http://atlas.apache.org/InstallationSteps.html
