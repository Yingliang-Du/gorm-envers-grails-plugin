package com.ylw.gorm.envers

import grails.test.mixin.*
import spock.lang.*

@TestFor(AuditedDomainController)
@Mock(AuditedDomain)
class AuditedDomainControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.auditedDomainList
            model.auditedDomainCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.auditedDomain!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def auditedDomain = new AuditedDomain()
            auditedDomain.validate()
            controller.save(auditedDomain)

        then:"The create view is rendered again with the correct model"
            model.auditedDomain!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            auditedDomain = new AuditedDomain(params)

            controller.save(auditedDomain)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/auditedDomain/show/1'
            controller.flash.message != null
            AuditedDomain.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def auditedDomain = new AuditedDomain(params)
            controller.show(auditedDomain)

        then:"A model is populated containing the domain instance"
            model.auditedDomain == auditedDomain
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def auditedDomain = new AuditedDomain(params)
            controller.edit(auditedDomain)

        then:"A model is populated containing the domain instance"
            model.auditedDomain == auditedDomain
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/auditedDomain/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def auditedDomain = new AuditedDomain()
            auditedDomain.validate()
            controller.update(auditedDomain)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.auditedDomain == auditedDomain

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            auditedDomain = new AuditedDomain(params).save(flush: true)
            controller.update(auditedDomain)

        then:"A redirect is issued to the show action"
            auditedDomain != null
            response.redirectedUrl == "/auditedDomain/show/$auditedDomain.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/auditedDomain/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def auditedDomain = new AuditedDomain(params).save(flush: true)

        then:"It exists"
            AuditedDomain.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(auditedDomain)

        then:"The instance is deleted"
            AuditedDomain.count() == 0
            response.redirectedUrl == '/auditedDomain/index'
            flash.message != null
    }
}
