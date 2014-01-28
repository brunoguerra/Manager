package com.ajurasz.service;

import com.ajurasz.model.*;
import com.ajurasz.repository.*;
import com.ajurasz.util.forms.InvoiceForm;
import com.ajurasz.util.pdf.GeneratePDF;
import com.ajurasz.util.sql.mapper.CityPostCode;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ajurasz
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    public final static BigDecimal VAT = new BigDecimal(123);
    public final static String VAT_DIS = "23";
    public final static BigDecimal EXCISE = new BigDecimal(37);

    private CustomerRegularRepository customerRepo;
    private ItemRepository itemRepo;
    private StateHistoryRepository stateHistoryRepo;
    private StateRepository stateRepo;
    private OrderRepository orderRepo;
    private ReasonRepository reasonRepo;
    private CompanyRepository companyRepo;
    private ReportRepository reportRepo;
    private CustomerVatRepository customerVatRepo;
    private RoleRepository roleRepo;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    public ManagerServiceImpl(CustomerRegularRepository customerRepo, ItemRepository itemRepo,
                              StateHistoryRepository stateHistoryRepo,StateRepository stateRepo,
                              OrderRepository orderRepo, ReasonRepository reasonRepo,
                              CompanyRepository companyRepo, ReportRepository reportRepo,
                              CustomerVatRepository customerVatRepo, RoleRepository roleRepo) {
        this.customerRepo = customerRepo;
        this.itemRepo = itemRepo;
        this.stateHistoryRepo = stateHistoryRepo;
        this.stateRepo = stateRepo;
        this.orderRepo = orderRepo;
        this.reasonRepo = reasonRepo;
        this.companyRepo = companyRepo;
        this.reportRepo = reportRepo;
        this.customerVatRepo = customerVatRepo;
        this.roleRepo = roleRepo;
    }

    //CUSTOMERS
    @Override
    @Transactional
    public CustomerRegular saveCustomer(CustomerRegular customer) {
        customer.setCompany(getCompany());
        return customerRepo.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerRegular> findAllCustomers() {
        return customerRepo.findAllByCompany(getCompany(), new Sort(Sort.Direction.DESC, "id"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerRegular> findAllCustomers(Pageable pageable) {
        return customerRepo.findAllByCompany(getCompany(), pageable);
    }

    @Override
    public List<CustomerRegular> findAllByCustomerLastName(String lastName) {
        return customerRepo.findAllByCustomerLastNameAndCompany(lastName, getCompany());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerRegular getCustomer(Long id) {
        CustomerRegular c = customerRepo.findCustomerByIdAndCompany(id, getCompany());
        return c;
    }

    @Override
    @Transactional
    public void deleteCustomer(CustomerRegular customer) {
        customerRepo.delete(customer);
    }

    @Override
    @Transactional
    public CustomerVat saveCustomerVat(CustomerVat customerVat) {
        customerVat.setCompany(getCompany());
        return customerVatRepo.save(customerVat);
    }

    @Override
    public Page<CustomerVat> findAllCustomersVat(Pageable pageable) {
        return customerVatRepo.findAllByCompany(getCompany(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerVat> findAllByCustomerVatName(String name) {
        return customerVatRepo.findAllByCustomerVatNameAndCompany(name, getCompany());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerVat getCustomerVat(Long id) {
        return customerVatRepo.findCustomerByIdAndCompany(id, getCompany());
    }

    @Override
    @Transactional
    public void deleteCustomerVat(CustomerVat customer) {
        customerVatRepo.delete(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityPostCode> findAllCitiesAndPostCodes(String cityName) {
        return customerRepo.findAllCitiesAndPostCodes(cityName);
    }

    //ITEMS


    //todo: Create logic for filling all fields
    @Override
    @Transactional
    public Item saveItem(Item item) {
        boolean isNew = false;
        if(item.getId() == null) {
            isNew = true;
        }
        item.setCompany(getCompany());

        //PriceNet = PriceGross * 100 /123
        item.setPriceNet( item.getPriceGross().multiply(new BigDecimal(100)).divide(VAT, 2, RoundingMode.HALF_UP));

        //PriceGrossExcise = PriceGross + 37zł
        item.setPriceGrossExcise( item.getPriceGross().add(EXCISE) );

        //PriceNetExcise = PriceGrossExcise * 100 /123
        item.setPriceNetExcise( item.getPriceGrossExcise().multiply(new BigDecimal(100)).divide(VAT, 2, RoundingMode.HALF_UP));

        itemRepo.save(item);

        if(isNew) {
            StateHistory stateHistory = null;
            if(item.getState() != null) {
                stateHistory = new StateHistory();
                stateHistory.setState(item.getState());
                stateHistory.setValue(item.getState().getCurrentState());
                stateHistoryRepo.save(stateHistory);
            }
        }

        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Long id) {
        Item item = itemRepo.findItemByIdAndCompany(id, getCompany());
        return item;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAllItems() {
        return itemRepo.findAllByCompany(getCompany());
    }

    @Override
    public Map<String, String> findAllItemsMap() {
        Map<String, String> result = new HashMap<String, String>();
        List<Item> items = itemRepo.findAllByCompany(getCompany());
        if(items != null && !items.isEmpty()) {
            for(Item item : items) {
                result.put(item.getId().toString(), item.getName());
            }
            return result;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteItem(Item item) {
        itemRepo.delete(item);
    }

    @Override
    @Transactional
    public StateHistory saveStateHistory(StateHistory stateHistory) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateHistory> findAllStateHistoryByStateIdDesc(Long id) {
        List<StateHistory> stateHistories = stateHistoryRepo.findAllByStateIdOrderByDateDesc(id);
        return stateHistories;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StateHistory> findAllStateHistoryByStateIdDesc(Long id, Pageable pageable) {
        return stateHistoryRepo.findAllByStateIdOrderByDateDesc(id, pageable);
    }

    @Override
    @Transactional
    public Reason saveReason(Reason reason) {
        reason.setCompany(getCompany());
        return reasonRepo.save(reason);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reason> findAllReasons() {
        return reasonRepo.findAllByCompany(getCompany());
    }

    @Override
    @Transactional(readOnly = true)
    public String getNextDocNumnber() {
        String docNumber = null;
        //Get current time is expected format
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String currentMonth = new SimpleDateFormat("MM").format(calendar.getTime());
        String currentYear = String.valueOf(calendar.get(Calendar.YEAR));

        Order order = orderRepo.getLatestOrder(getCompany().getId());

        //First entry in db
        if(order == null) {
            return "1/" + currentMonth + "/" + currentYear;
        }

        //docNumber exist but new month or year is present
        String[] latestDocNumber = order.getDocNumber().split("/");
        if(!latestDocNumber[1].equals(currentMonth) || !latestDocNumber[2].equals(currentYear)) {
            return "1/" + currentMonth + "/" + currentYear;
        }

        //just increment first number
        int id = Integer.parseInt(latestDocNumber[0]);
        id++;
        return id + "/" + currentMonth + "/" + currentYear;
    }

    @Override
    @Transactional(readOnly = true)
    public String getNextInvoiceNumnber() {
        String docNumber = null;
        //Get current time is expected format
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String currentMonth = new SimpleDateFormat("MM").format(calendar.getTime());
        String currentYear = String.valueOf(calendar.get(Calendar.YEAR));

        Order order = orderRepo.getLatestInvoice(getCompany().getId());

        //First entry in db
        if(order == null) {
            return "01/" + currentMonth + "/" + currentYear;
        }

        String[] latestDocNumber = order.getInvoiceNumber().split("/");
        //just increment first number
        int id = Integer.parseInt(latestDocNumber[0]);
        id++;
        return String.format("%02d", id) + "/" + currentMonth + "/" + currentYear;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllOrders(Pageable pageable) {
        return orderRepo.findAllByCompany(getCompany(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Order> findAllInvoices(Pageable pageable) {
        return orderRepo.findAllByCompanyAndInvoice(getCompany(), true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAllOrders() {
        return orderRepo.findAllByCompany(getCompany());
    }

    @Override
    @Transactional(readOnly = true)
    public State getState(Long id) {
        return stateRepo.findOne(id);
    }

    @Override
    @Transactional
    public State saveState(State state) {
        stateRepo.save(state);
        StateHistory stateHistory = new StateHistory();
        stateHistory.setState(state);
        stateHistory.setValue(state.getLastValue());
        stateHistoryRepo.save(stateHistory);
        return state;
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        Customer customer = customerRepo.findOne(order.getCustomer().getId());
        List<OrderDetails> orderDetailses = order.getOrderDetails();
        List<OrderDetails> resultList = new ArrayList<OrderDetails>();
        for (OrderDetails orderDetails : orderDetailses) {
            if(orderDetails.getItem() == null || orderDetails.getReason() == null || (orderDetails.getQuantity() == new BigDecimal(0)) ) {
                continue;
            }
            Item item = itemRepo.findOne(orderDetails.getItem().getId());
            Reason reason = reasonRepo.findOne(orderDetails.getReason().getId());
            BigDecimal quantity = orderDetails.getQuantity().divide(new BigDecimal(1000));

            //set orderDetails
            orderDetails.setOrder(order);
            orderDetails.setItem(item);
            orderDetails.setReason(reason);

            //set prices
            orderDetails.setPriceGross( item.getPriceGross().multiply(quantity) );
            orderDetails.setPriceNet(item.getPriceNet().multiply(quantity));
            orderDetails.setPriceGrossExcise(item.getPriceGrossExcise().multiply(quantity));
            orderDetails.setPriceNetExcise(item.getPriceNetExcise().multiply(quantity));

            //set state history
            State state = item.getState();
            state.setCurrentState(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateRepo.save(state);

            StateHistory history = new StateHistory();
            history.setState(state);
            history.setValue(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateHistoryRepo.save(history);

            resultList.add(orderDetails);
        }

        //set order
        if (order.getDocNumber() == null)
            order.setDocNumber(getNextDocNumnber());

        if(order.getOrderDate() == null)
            order.setOrderDate(DateTime.now());

        //rename
        order.setCustomer(customer);
        order.setCompany(getCompany());
        order.setOrderDetails(resultList);

        //todo: execute two tasks in paraller
        //save order to disk
        saveOrderToDisk(order, servletContext.getRealPath("/WEB-INF/pdfs/documents/" + getCompany().getId()), false);

        //save order to db
        return orderRepo.save(order);
    }

    @Override
    @Transactional
    public Order update(Order order) {
        Order before = orderRepo.findByIdAndCompany(order.getId(), getCompany());
        deleteOrder(before);
        return saveOrder(order);
    }

    private void saveOrderToDisk(Order order, String dest, boolean invoice) {
        GeneratePDF generatePDF = new GeneratePDF(getCompany(), dest);
        generatePDF.generate(order, invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        return orderRepo.findByIdAndCompany(id, getCompany());
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {
        Order actualOrder = orderRepo.findByIdAndCompany(order.getId(), getCompany());
        //update current state
        for(OrderDetails orderDetails : actualOrder.getOrderDetails()) {
            State state = orderDetails.getItem().getState();
            state.setCurrentState(orderDetails.getQuantity());
            stateRepo.save(state);

            StateHistory history = new StateHistory();
            history.setState(state);
            history.setValue(orderDetails.getQuantity(), true);
            stateHistoryRepo.save(history);
        }

        orderRepo.delete(actualOrder);
    }

    @Override
    @Transactional
    public Company saveCompany(Company company) {
        return  companyRepo.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        return roleRepo.findByAuthority(roleName);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Report> findAllReports(Pageable pageable) {
        return reportRepo.findAllByCompany(getCompany(), pageable);
    }

    @Override
    @Transactional
    public Report saveReport(Report report) {
        report.setCreationDate(DateTime.now());
        report.setReportName(DateTime.now().toString("dd-MM-yyy-hh-mm"));
        report.setCompany(getCompany());
        saveReportToDisk(report, servletContext.getRealPath("/WEB-INF/pdfs/reports/" + getCompany().getId()));
        return reportRepo.save(report);
    }

    private void saveReportToDisk(Report report, String dest) {
        List<Order> orders =
                orderRepo.findAllByCompanyBetweenDates(getCompany(), report.getStartDate(), report.getEndDate());

        GeneratePDF generatePDF = new GeneratePDF(getCompany(), dest);
        generatePDF.generate(report, validateOrdersBaseOnInvoiceContent(orders));
    }

    private List<Order> validateOrdersBaseOnInvoiceContent(List<Order> orders) {
        List<Order> newOrders = new ArrayList<Order>();
        for(Order order : orders) {
            if(order.getDocNumber() != null) {
                newOrders.add(order);
            }
        }

        return newOrders;
    }

    @Override
    @Transactional(readOnly = true)
    public Report getReport(Long id) {
        return reportRepo.findByIdAndCompany(id, getCompany());
    }

    @Override
    @Transactional
    public void deleteReport(Report report) {
        reportRepo.delete(report);
    }

    @Override
    public Company getCompany() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Company company = (Company)auth.getPrincipal();
        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> calculateInvoice(Long id, BigDecimal quantity) {
        Map<String, String> result = new HashMap<String, String>();
        Item item = getItem(id);


        result.put("priceGross", item.getPriceGross().multiply(quantity.divide(new BigDecimal(1000))).setScale(2, RoundingMode.HALF_UP).toString());
        result.put("priceNet", item.getPriceNet().multiply(quantity.divide(new BigDecimal(1000))).setScale(2, RoundingMode.HALF_UP).toString());
        result.put("priceGrossExcise", item.getPriceGrossExcise().multiply(quantity.divide(new BigDecimal(1000))).setScale(2, RoundingMode.HALF_UP).toString());
        result.put("priceNetExcise", item.getPriceNetExcise().multiply(quantity.divide(new BigDecimal(1000))).setScale(2, RoundingMode.HALF_UP).toString());
        return result;
    }

    @Override
    public void saveInvoiceForm(InvoiceForm invoiceForm) {
        CustomerVat customer = (CustomerVat)customerVatRepo.findOne(invoiceForm.getOrder().getCustomer().getId());
        List<OrderDetails> orderDetailses = invoiceForm.getOrder().getOrderDetails();
        List<OrderDetails> resultList = new ArrayList<OrderDetails>();
        for (OrderDetails orderDetails : orderDetailses) {
            if(orderDetails.getItem() == null || (orderDetails.getQuantity() == new BigDecimal(0)) ) {
                continue;
            }
            Item item = itemRepo.findOne(orderDetails.getItem().getId());
            Reason reason = reasonRepo.findOne(orderDetails.getReason().getId());
            //set orderDetails
            orderDetails.setReason(reason);
            orderDetails.setOrder(invoiceForm.getOrder());
            orderDetails.setItem(item);

            BigDecimal quantity = orderDetails.getQuantity().divide(new BigDecimal(1000));
            //set prices
            if(orderDetails.getPriceGross() == null)
                orderDetails.setPriceGross( item.getPriceGross().multiply(quantity) );
            if(orderDetails.getPriceNet() == null)
                orderDetails.setPriceNet(item.getPriceNet().multiply(quantity));
            if(orderDetails.getPriceGrossExcise() == null)
                orderDetails.setPriceGrossExcise(item.getPriceGrossExcise().multiply(quantity));
            if(orderDetails.getPriceNetExcise() == null)
                orderDetails.setPriceNetExcise(item.getPriceNetExcise().multiply(quantity));

            //set state history
            State state = item.getState();
            state.setCurrentState(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateRepo.save(state);

            StateHistory history = new StateHistory();
            history.setState(state);
            history.setValue(orderDetails.getQuantity().multiply(new BigDecimal(-1)));
            stateHistoryRepo.save(history);

            resultList.add(orderDetails);
        }

        //set order
        if (invoiceForm.getOrder().getInvoiceNumber() == null)
            invoiceForm.getOrder().setInvoiceNumber(getNextInvoiceNumnber());

        //rename
        invoiceForm.getOrder().setDocument(false);
        invoiceForm.getOrder().setInvoice(true);
        if(invoiceForm.getOrder().getOrderDate() == null)
            invoiceForm.getOrder().setOrderDate(DateTime.now());
        invoiceForm.getOrder().setCustomer(customer);
        invoiceForm.getOrder().setCompany(getCompany());
        invoiceForm.getOrder().setOrderDetails(resultList);

        //todo: execute two tasks in paraller
        //save order to disk
        if(!invoiceForm.isExcise()) {
            invoiceForm.getOrder().setDocument(true);
            invoiceForm.getOrder().setDocNumber(getNextDocNumnber());
            saveOrderToDisk(invoiceForm.getOrder(), servletContext.getRealPath("/WEB-INF/pdfs/documents/" + getCompany().getId()), true);
        }
        //save invoice to disk
        saveInvoiceToDisk(invoiceForm, servletContext.getRealPath("/WEB-INF/pdfs/invoices/" + getCompany().getId()));
        //save order to db
        orderRepo.save(invoiceForm.getOrder());
    }

    private void saveInvoiceToDisk(InvoiceForm invoiceForm, String dest) {
        GeneratePDF generatePDF = new GeneratePDF(getCompany(), dest);
        if(invoiceForm.isGrossInvoice()) {
            generatePDF.generateGross(invoiceForm);
        } else {
            generatePDF.generateNet(invoiceForm);
        }
    }
}
