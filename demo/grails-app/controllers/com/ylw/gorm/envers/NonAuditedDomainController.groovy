package com.ylw.gorm.envers

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class NonAuditedDomainController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	//---------gorm-envers----------
	def testTrait() {
		def domain = new NonAuditedDomain()
	    render "The current date is -> " + domain.currentDate()
	}
	
	def isAudited() {
		def domain = new NonAuditedDomain()
	    render "Is the domain audited -> " + domain.audited()
	}
	
	def findAllRevisions() {
		def domain = new NonAuditedDomain()
		if (domain.audited()) {
	    	render "All Revisions -> " + domain.findAllRevisions()
		}
		else {
			render "The domain is not audited!"
		}
	}
	//---------end of gorm-envers----------

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NonAuditedDomain.list(params), model:[nonAuditedDomainCount: NonAuditedDomain.count()]
    }

    def show(NonAuditedDomain nonAuditedDomain) {
        respond nonAuditedDomain
    }

    def create() {
        respond new NonAuditedDomain(params)
    }

    @Transactional
    def save(NonAuditedDomain nonAuditedDomain) {
        if (nonAuditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (nonAuditedDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond nonAuditedDomain.errors, view:'create'
            return
        }

        nonAuditedDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'nonAuditedDomain.label', default: 'NonAuditedDomain'), nonAuditedDomain.id])
                redirect nonAuditedDomain
            }
            '*' { respond nonAuditedDomain, [status: CREATED] }
        }
    }

    def edit(NonAuditedDomain nonAuditedDomain) {
        respond nonAuditedDomain
    }

    @Transactional
    def update(NonAuditedDomain nonAuditedDomain) {
        if (nonAuditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (nonAuditedDomain.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond nonAuditedDomain.errors, view:'edit'
            return
        }

        nonAuditedDomain.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'nonAuditedDomain.label', default: 'NonAuditedDomain'), nonAuditedDomain.id])
                redirect nonAuditedDomain
            }
            '*'{ respond nonAuditedDomain, [status: OK] }
        }
    }

    @Transactional
    def delete(NonAuditedDomain nonAuditedDomain) {

        if (nonAuditedDomain == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        nonAuditedDomain.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'nonAuditedDomain.label', default: 'NonAuditedDomain'), nonAuditedDomain.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'nonAuditedDomain.label', default: 'NonAuditedDomain'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
