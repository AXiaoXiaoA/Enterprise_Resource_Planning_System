create table "PersonalInformation"
(
    ID              VARCHAR2(100) not null
        constraint "PersonalInformation_pk"
            primary key,
    NAME            VARCHAR2(255) not null,
    GENDER          VARCHAR2(100),
    NATIONALITY     VARCHAR2(255),
    BIRTHDAY        DATE,
    BIRTHPLACE      VARCHAR2(255),
    POLITICALSTATUS VARCHAR2(100),
    TEL             NUMBER,
    EMAIL           VARCHAR2(255)
)
/

create table "Employee"
(
    ID         NUMBER        not null
        primary key,
    PASSWORD   VARCHAR2(255),
    DEPARTMENT VARCHAR2(100),
    POSITION   VARCHAR2(100),
    STARTDATE  DATE,
    ENDDATE    DATE,
    STATUS     VARCHAR2(100),
    RESUME     CLOB,
    PERSON_ID  VARCHAR2(255) not null
        constraint "Employee_PersonalInformation_ID_fk"
            references "PersonalInformation"
)
/

create trigger EMPLOYEE_ID_TRIGGER
    before insert
    on "Employee"
    for each row
begin
    select EMPLOYEE_ID_SEQ.nextval into :new.ID from dual;
end EMPLOYEE_ID_TRIGGER;
/

create table "Material"
(
    ID        NUMBER not null
        constraint "Material_pk"
            primary key,
    NAME      VARCHAR2(255),
    PRICE     NUMBER,
    INVENTORY NUMBER,
    REMAINING NUMBER
)
/

create table "Product"
(
    ID               NUMBER        not null
        constraint "Product_pk"
            primary key,
    NAME             VARCHAR2(255) not null,
    PRICE            NUMBER,
    INVENTORY        NUMBER,
    MATERIAL_ID      NUMBER
        constraint "Product_Material_ID_fk"
            references "Material",
    REMAINING        NUMBER,
    MATERIAL_CONSUME NUMBER
)
/

create trigger PRODUCT_ID_TRIGGER
    before insert
    on "Product"
    for each row
begin
    select PRODUCT_ID_SEQ.nextval into :new.ID from dual;
end PRODUCT_ID_TRIGGER;
/

create trigger MATERIAL_ID_TRIGGER
    before insert
    on "Product"
    for each row
begin
    select MATERIAL_ID_SEQ.nextval into :new.ID from dual;
end MATERIAL_ID_TRIGGER;
/

create table "Company"
(
    ID   NUMBER not null,
    NAME VARCHAR2(255),
    TEL  NUMBER
)
/

create unique index "Customer_pk"
    on "Company" (ID)
/

alter table "Company"
    add constraint "Company_pk"
        primary key (ID)
/

create table "SalesOrder"
(
    ID                NUMBER not null
        constraint "SalesOrder_pk"
            primary key,
    PRODUCT_ID        NUMBER not null
        constraint "SalesOrder_Product_ID_fk"
            references "Product",
    SALES_EMPLOYEE_ID NUMBER not null
        constraint "SalesOrder_Employee_ID_fk"
            references "Employee",
    QUANTITY          NUMBER,
    CONTRACT          BLOB,
    APPLY_DATE        DATE,
    COMPANY_ID        NUMBER not null
        constraint "SalesOrder_Customer_ID_fk"
            references "Company",
    REPO_EMPLOYEE_ID  NUMBER
        constraint "SalesOrder_Employee_ID_fk2"
            references "Employee",
    STATUS            VARCHAR2(255)
)
/

create trigger SALESORDER_ID_TRIGGER
    before insert
    on "SalesOrder"
    for each row
begin
    select SALESORDER_ID_SEQ.nextval into :new.ID from dual;
end SALESORDER_ID_TRIGGER;
/

create table "PurchasingOrder"
(
    ID                     NUMBER not null
        constraint "PurchasingOrder_pk"
            primary key,
    MATERIAL_ID            NUMBER not null
        constraint "PurchasingOrder_Material_ID_fk"
            references "Material",
    PURCHASING_EMPLOYEE_ID NUMBER not null
        constraint "PurchasingOrder_Employee_ID_fk"
            references "Employee",
    QUANTITY               NUMBER,
    CONTRACT               BLOB,
    APPLY_DATE             DATE,
    COMPANY_ID             NUMBER not null
        constraint "PurchasingOrder_Customer_ID_fk"
            references "Company",
    REPO_EMPLOYEE_ID       NUMBER
        constraint "PurchasingOrder_Employee_ID_fk2"
            references "Employee",
    STATUS                 VARCHAR2(255)
)
/

create trigger PURCHASINGORDER_ID_TRIGGER
    before insert
    on "PurchasingOrder"
    for each row
begin
    select PURCHASINGORDER_ID_SEQ.nextval into :new.ID from dual;
end PURCHASINGORDER_ID_TRIGGER;
/

create trigger COMPANY_ID_TRIGGER
    before insert
    on "Company"
    for each row
begin
    select COMPANY_ID_SEQ.nextval into :new.ID from dual;
end COMPANY_ID_TRIGGER;
/


