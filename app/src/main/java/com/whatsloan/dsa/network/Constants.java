package com.whatsloan.dsa.network;

/**
 * Created by S.K. Pissay on 5/3/16.
 */
public class Constants {

    public static final String AUTHKEY = "user-key";
    public static final String LOCATIONS_API = "locations";
    public static final String LOCATION_DETAILS = "location_details";
    public static final String SEARCH_QUERY = "q";

    public static final String ENTITY_ID = "entity_id";
    public static final String ENTITY_TYPE = "entity_type";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lon";

    private String API_KEY = "07b2294e08dd61ee580be44c23a91c1b";

    public String getApiKey() {
        return API_KEY;
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String AUTHLOGIN = "auth/login";
    public static final String OTP_VERIFY = "auth/otp/verify";
    public static final String LEADSLIST = "leads";
    public static final String CUSTOMER = "customer";
    public static final String LEAD = "leads";
    public static final String CUSTOMERS = "customers";
    public static final String LANDING = "home";
    public static final String CAMPAIGNS = "campaigns";
    public static final String ATTENDANCES = "attendances";
    public static final String ATTENDANCE_CALENDAR = "attendances/calendar";
    public static final String AUTHLOGOUT = "auth/logout";
    public static final String PDF_UPLOAD = "document";

    //old Struct
//    public static final String PROJECTS = "projects";
//    public static final String BANKSAPPROVING = "banks/approving";
//    public static final String PROJECTSAPPROVAL = "projects/to-be-approved";

    //new Struct
    public static final String PROJECTS = "banks/projects/approved";
    public static final String BANKSAPPROVING = "banks/projects/approved-by";
    public static final String PROJECTSAPPROVAL = "banks/projects/pending";

    public static final String PROJECT_PICTURE = "project_picture";
    public static final String SINGLE_PROJECT = "projects";

    public static final String CITIES = "cities";
    public static final String BANKS = "banks";
    public static final String BUILDER = "builder";
    public static final String BUILDERS = "builders";
    public static final String USERTEAM = "user/team";
    public static final String TASKUSERTEAM = "task/user/team";
    public static final String USER_LOANS = "user/loans";
    public static final String USERS = "users";
    public static final String USER = "user";
    public static final String PROJECT_STATUSES = "project-statuses";
    public static final String TASK_UUID = "task_uuid";
    public static final String TASK_STATUSES = "taskstatus";
    public static final String TASK_STAGES = "taskstages";
    public static final String TODAYS_TASK = "tasks/todayTasks";
    public static final String TASKS_USER = "tasks.user";
    public static final String TASKS_STAGE = "tasks.stage";
    public static final String TASKS_STATUS = "tasks.status";
    public static final String TASKS_MEMBERS = "tasks.members";
    public static final String TEAMMEMBERS_BANKS = "team/members/banks";
    public static final String USERTEAM_MEMBERS = "user/team/members";
    public static final String SEARCH_API = "search";
    public static final String LEADS_SOURCE = "leads/source";
    public static final String LOANS_TYPE = "loans/type";
    public static final String TEAMS_REFERRALS = "teams/referrals";
    public static final String BANKS_PRODUCTS_GETPRODUCTS = "banks/products/getProducts";
    public static final String BANKS_RODUCTS_DOCUMENTFILTERS = "banks/products/documentFilters";
    public static final String BUILDERS_GETBUILDERS = "builders/getBuilders";
    public static final String PROJECTS_GETPROJECT = "projects/getProjects";
    public static final String DASHBOARD_TEAMS = "dashboard/teams";
    public static final String DASHBOARD_BUILDERS = "dashboard/builders";
    public static final String DASHBOARD_REFERRALS = "dashboard/referrals";
    public static final String SEARCH_CUSTOMERS = "search/customers";
    public static final String SEARCH_LEADS = "search/leads";
    public static final String TASKS_UPDATESTATUS = "tasks/updateStatus";
    public static final String TASKS_GETLOANTASKS = "loans/tasks";
    public static final String LOANS_STATUSES = "loans/statuses";
    public static final String REMARKS = "remarks";
    public static final String USER_UUID = "user_uuid";
    public static final String LOGIN_DATE = "login_date";
    public static final String LEAD_DATE = "lead_date";
    public static final String LOAN_UUID = "loan_uuid";
    public static final String _METHOD = "_method";
    /*name   - Name of the project
                builder_uuid - Name of the builder
                alpha_street - Location
                city_uuid - city uuid
                bank_uuid - bank uuid
                assignee - user uuid
                status - status of the build
                unit_details - unit detail
                possession_date - date
                status - status of the project*/

    public static final String INSCLUDE = "include";
    public static final String REFERRALS = "referrals";
    public static final String REFERRAL = "referral";
    public static final String SETTINGS = "settings";
    public static final String LOANS = "loans";
    public static final String LOANS_TOTAL_TAT = "loans.total_tat";
    public static final String LOANS_LOAN_STATUS_TAT = "loans.loan_status_tat";
    public static final String LOANS_ATTACHMENTS= "loans.attachments";
    public static final String LOANS_TEAM= "loans.team";
    public static final String LOANS_HISTORY= "loans.history";
    public static final String LOANS_AGENT_BANKS= "loans.agent.banks";
    public static final String ADDRESSES = "addresses";
    public static final String CUSTOMER_SETTINGS = "customer.settings";
    public static final String CUSTOMER_LOANS = "customer.loans";
    public static final String CUSTOMER_ADDRESSES = "customer.addresses";
    public static final String USER_ADDRESSES = "user.addresses";
    public static final String ADDRESS = "address";
    public static final String ATTACHMENTS = "attachments";
    public static final String OWNER = "owner";
    public static final String TASKS = "tasks";
    public static final String ASSIGNEE = "assignee";
    public static final String BANKID = "bank_uuid";
    public static final String CITYID = "city_uuid";
    public static final String TYPE_UUID = "type_uuid";
    public static final String LOAN_AMOUNT = "loan_amount";
    public static final String NET_SALARY = "net_salary";
    public static final String EXISTING_LOAN_EMI = "existing_loan_emi";
    public static final String COMPANY_NAME = "company_name";
    public static final String PROJECTID = "project_uuid";
    public static final String QUERY = "query";
    public static final String PAGINATE = "paginate";
    public static final String MEMBERS = "members";
    public static final String QUERIES = "queries";
    public static final String AGENT_ID = "agent_id";
    public static final String BANK_ID = "bank_id";
    public static final String ALL = "all";
    public static final String FROM_DATE = "from_date"; //yyyy-mm-dd
    public static final String TO_DATE = "to_date";
    public static final String FROM = "from"; //yyyy-mm-dd
    public static final String TO = "to";
    public static final String APPROVINGBANKS = "banks/approving";
    public static final String LOAN_STATUS_UUID = "loan_status_uuid";
    public static final String LOANS_UPLOAD = "loans/upload";
    public static final String LOANS_UPDATESTATUS = "loans/updateStatus";

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String BUILDER_UUID = "builder_uuid";
    public static final String MEMBER_UUID = "member_uuid";
    public static final String PERIOD = "period";
    public static final String MEMBER_UUID_ARRAY = "member_uuid[]";
    public static final String ALPHA_STREET = "alpha_street";
    public static final String STREET = "street";
    public static final String CITY_UUID = "city_uuid";
    public static final String STATUS = "status";
    public static final String STAGE = "stage";
    public static final String UNIT_DETAILS = "unit_details";
    public static final String DESCRIPTION = "description";
    public static final String POSSESSION_DATE = "possession_date";
    public static final String LSR_START_DATE = "lsr_start_date";
    public static final String LSR_END_DATE = "lsr_end_date";

    public static final String PROJECTS_BANKS = "project.banks";
    public static final String PROJECTS_ADDRESS = "project.address";
    public static final String PROJECTS_OWNER = "project.owner";
    public static final String PROJECTS_ATTACHMENTS = "project.attachments";

    public static final String ASSIGNEDTO = "assigned_to";
    public static final String RAISEDDATE = "raised_date";
    public static final String STARTDATE = "start_date";
    public static final String ENDDATE = "end_date";
    public static final String DUEDATE = "due_date";
    public static final String PENDINGWITH = "pending_with";
    public static final String AGENT_UUID = "agent_uuid";
    public static final String PRIORITY = "priority";
    public static final String ASSIGNED_TEAM_UUID = "assigned_team_uuid";
    public static final String ASSIGNED_TO_UUID = "assigned_to_uuid";
    public static final String TASK_STATUS_UUID = "task_status_uuid";
    public static final String TASK_STAGE_UUID = "task_stage_uuid";
    public static final String TASKABLE_UUID = "taskable_uuid";
    public static final String TASKABLE_TYPE = "taskable_type";
    public static final String SOURCE_UUID = "source_uuid";
    public static final String AGENT_BANKS = "agent.banks";
    public static final String REFERRAL_UUID = "referral_uuid";
    public static final String HOME_LOAN = "Home Loan";
    public static final String PERSONAL_LOAN = "Personal Loan";
    public static final String RESIDENT_STATUS = "resident_status";
    public static final String PROFESSION = "profession";
    public static final String DOB = "dob";
    public static final String AGE = "age";
    public static final String EDUCATION = "education";
    public static final String MARITAL_STATUS = "marital_status";
    public static final String COMPANY = "company";
    public static final String NET_INCOME = "net_income";
    public static final String PAN = "pan";
    public static final String SALARY_BANK = "salary_bank";
    public static final String SKYPE = "skype";
    public static final String FACETIME = "facetime";
    public static final String CONTACT_TIME = "contact_time";
    public static final String CIBIL_SCORE = "cibil_score";
    public static final String CIBIL_STATUS = "cibil_status";
    public static final String STARTDAY = "startday";


    public static final String PENDING = "PENDING";
    public static final String APPROVED = "APPROVED";

    public static final String ID_PROOF = "ID_PROOF";
    public static final String EXPERIENCE_DOCUMENT = "EXPERIENCE_DOCUMENT";
    public static final String ADDRESS_PROOF = "ADDRESS_PROOF";
    public static final String PRODUCT_DOCUMENT = "PRODUCT_DOCUMENT";
    public static final String PROFILE_PICTURE = "PROFILE_PICTURE";

    public static final String MONTH = "month";
    public static final String YEAR = "year";

    public static final String CUSTOMER_SEARCH = "Customer Name";
    public static final String PHONE_SEARCH = "Phone";
    public static final String APPID_SEARCH = "App Id";
    public static final String LOANTYPE_SEARCH = "Loan Type";
    public static final String UNITNO_SEARCH = "Unit No.";
    public static final String BUILDER_SEARCH = "Builder Name";
    public static final String PROJECT_SEARCH = "Project Name";
    public static final String LOAN_STATUS = "Loan Status";
    public static final String FROM_DATESER = "From Date";
    public static final String TO_DATESER = "Login Date";

    public static final String STATUSGROUP = "statusgroup";
    public static final String LOGINS = "logins";
    public static final String DISBURSEMENTS = "disbursements";
    public static final String FIRST_DISB = "first_disb";
    public static final String PART_DISB = "part_disb";
    public static final String FULL_DISB = "full_disb";

    public static final String TASK_STATUS_UUID_STATUS = "task_status_uuid";
    public static final String REMARKS_STATUS = "remarks";
    public static final String MESSAGES = "messages";
    public static final String DATA = "data";


    public static String apiMethodEx(String apiMethod, String uuid) {
        StringBuffer lBuf = new StringBuffer();
        if (null != uuid) {
            return lBuf.append(apiMethod).append("/").append(uuid).toString();
        } else {
            return lBuf.append(apiMethod).toString();
        }
    }
}
