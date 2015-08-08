# gorm-envers-grails-plugin
A Grails Plugin for Auditing GORM Domain class using Hibernate Envers. This plugin only work with Grails 3 or later.

## Using the gorm-envers plugin in your Grails application

Add the release version of the plugin dependency in build.gradle
```
compile ("org.grails.plugins:gorm-envers:0.2") {
	exclude module: 'hibernate-core'
	exclude module: 'hibernate-entitymanager'
}
```

## Build from source 

### Install gorm-envers plugin on your machine
```
git clone https://github.com/Yingliang-Du/gorm-envers-grails-plugin.git
cd gorm-envers-grails-plugin/gorm-envers
grails clean
grails compile
grails install
```
### Add the installed plugin dependency in build.gradle
```
compile ("org.grails.plugins:gorm-envers:0.3-SNAPSHOT") {
	exclude module: 'hibernate-core'
	exclude module: 'hibernate-entitymanager'
}
```
## Audit domain classes in your Grails application
Add @Audited annotation to the domain class you want to audit

That is it!

## Auditing methods added to domain classes
* ```findAllRevisions()```
* ```isAudited()```
* ```findCurrentRevision()```
* ```findRevisionNumberForDate()```

## Demo after install gorm-envers plugin
```
cd pathto/gorm-envers-grails-plugin/demo
grails 
grails> clean
grails> compile
grails> run-app
```

* Grails application running at http://localhost:8080
* Do CRUD operation on AuditedDomain and NonAuditedDomain with Grails default interface
* Check out the Database Console in browser at: http://localhost:8080/dbconsole
* Notice the audit table for AuditedDomain had been created: AUDITED_DOMAIN_AUD, and change history of that domain object was loged into this table
