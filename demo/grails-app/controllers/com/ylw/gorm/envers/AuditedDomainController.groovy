package com.ylw.gorm.envers

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AuditedDomainController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//---------gorm-envers----------
	def testTrait() {
		def domain = new AuditedDomain()
	    render "The current date is -> " + domain.currentDate()
	}
	
	def isAudited() {
		def domain = new AuditedDomain()
	    render "Is the domain audited -> " + domain.isAudited()
	}
	
	def findAllRevisions() {
		def domain = new AuditedDomain()
		if (domain.audited()) {
	    	render "All Revisions -> " + domain.findAllRevisions()
		}
		else {
			render "The domain is not audited!"
		}
	}

    def findCurrentRevision() {
        def domain = new AuditedDomain()
        render "The current revision of this domain -> " + domain.findCurrentRevision()
    }

    def findRevisionNumberForDate() {
        def domain = new AuditedDomain()
        render "The revision number for today -> " + domain.findRevisionNumberForDate()
    }

	//---------end of gorm-envers----------

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AuditedDomain.list(params), model:[auditedDomainCount: AuditedDomain.count()]
    }

    def show(AuditedDomain auditedDomain) {
        respond auditedDomain
    }

    def create() {
        respond new AuditedDomain(params)
    }

    @Transactional
    def save(AuditedDomain auditedDomain) {
        if (auditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (auditedDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditedDomain.errors, view:'create'
            return
        }

        auditedDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'auditedDomain.label', default: 'AuditedDomain'), auditedDomain.id])
                redirect auditedDomain
            }
            '*' { respond auditedDomain, [status: CREATED] }
        }
    }

    def edit(AuditedDomain auditedDomain) {
        respond auditedDomain
    }

    @Transactional
    def update(AuditedDomain auditedDomain) {
        if (auditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (auditedDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditedDomain.errors, view:'edit'
            return
        }

        auditedDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'auditedDomain.label', default: 'AuditedDomain'), auditedDomain.id])
                redirect auditedDomain
            }
            '*'{ respond auditedDomain, [status: OK] }
        }
    }

    @Transactional
    def delete(AuditedDomain auditedDomain) {

        if (auditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        auditedDomain.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'auditedDomain.label', default: 'AuditedDomain'), auditedDomain.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditedDomain.label', default: 'AuditedDomain'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
