package server.api;

import commons.Invoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.InvoiceRepository;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final InvoiceRepository invoiceRepository;

    /**
     * Constructor for the InvoiceController.
     *
     * @param invoiceRepository the repository for invoices
     */
    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Get all invoices.
     *
     * @return all invoices
     */
    @GetMapping(path = { "", "/" })
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    /**
     * Get an invoice by its id.
     *
     * @param id the id of the invoice
     * @return the invoice with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("id") long id) {
        if(!invoiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(invoiceRepository.findById(id).get());
    }

    /**
     * Edit an invoice.
     *
     * @param id the id of the invoice
     * @return the new invoice
     */
    @PutMapping("/{id}")
    public ResponseEntity<Invoice> editInvoice(@PathVariable("id") long id,
                                               @RequestBody Invoice updatedInvoice) {
        if(!invoiceRepository.existsById(id) || isNullOrEmptyInvoice(updatedInvoice)) {
            return ResponseEntity.badRequest().build();
        }
        Invoice saved = invoiceRepository.save(updatedInvoice);
        return ResponseEntity.ok(saved);
    }

    /**
     * Add an invoice.
     *
     * @param invoice the invoice to add
     * @return the added invoice
     */
    @PostMapping(path = { "", "/" })
    public ResponseEntity<Invoice> add(@RequestBody Invoice invoice) {
        if(isNullOrEmptyInvoice(invoice)) {
            return ResponseEntity.badRequest().build();
        }
        Invoice saved = invoiceRepository.save(invoice);
        return ResponseEntity.ok(saved);
    }

    /**
     * Delete an invoice.
     *
     * @param id the id of the invoice
     * @return the deleted invoice
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable long id) {
        if(!invoiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Invoice deleted = invoiceRepository.findById(id).get();
        invoiceRepository.deleteById(id);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Check if a string is null or empty.
     * @param s the string to check
     * @return true if the string is null or empty, false otherwise
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    /**
     * Check if an invoice is null or empty.
     * @param invoice the invoice to check
     * @return true if the invoice is null or empty, false otherwise
     */
    private static boolean isNullOrEmptyInvoice(Invoice invoice) {
        //TODO: Implement this method
        return invoice == null || isNullOrEmpty(invoice.getMeaning());
    }
}