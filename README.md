# gorm-envers-grails-plugin
A Grails Plugin for Auditing GORM Domain class using Hibernate Envers

## Install gorm-envers plugin on your machine
```
git clone https://github.com/Yingliang-Du/gorm-envers-grails-plugin.git
cd gorm-envers-grails-plugin/gorm-envers
grails clean
grails compile
grails install
```
## Audit domain classes in your Grails application

Add plugin dependency in build.gradle
```
compile ("org.grails.plugins:gorm-envers:0.1") {
	exclude module: 'hibernate-core'
	exclude module: 'hibernate-entitymanager'
}
```

Add @Audited annotation to the domain class you want to audit

That is it!
