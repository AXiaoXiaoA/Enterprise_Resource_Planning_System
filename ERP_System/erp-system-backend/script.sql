create sequence COMPANY_ID_SEQ
/

create sequence EMPLOYEE_ID_SEQ
/

create sequence MATERIAL_ID_SEQ
/

create sequence PRODUCT_ID_SEQ
/

create sequence PURCHASINGORDER_ID_SEQ
/

create sequence SALESORDER_ID_SEQ
/

create table "PersonalInformation"
(
    ID              VARCHAR2(50)  not null
        constraint "PersonalInformation_pk"
            primary key,
    NAME            VARCHAR2(100) not null,
    GENDER          VARCHAR2(10)  not null,
    NATIONALITY     VARCHAR2(50)  not null,
    BIRTHDAY        DATE          not null,
    BIRTHPLACE      VARCHAR2(100) not null,
    POLITICALSTATUS VARCHAR2(50)  not null,
    TEL             VARCHAR2(15)  not null,
    EMAIL           VARCHAR2(100) not null
)
/

create table "Employee"
(
    ID         NUMBER       not null
        primary key,
    PERSON_ID  VARCHAR2(50) not null
        constraint "Employee_PersonalInformation_ID_fk"
            references "PersonalInformation",
    DEPARTMENT VARCHAR2(50) not null,
    POSITION   VARCHAR2(50) not null,
    STARTDATE  DATE         not null,
    ENDDATE    DATE,
    STATUS     VARCHAR2(20) not null,
    PASSWORD   VARCHAR2(50),
    RESUME     CLOB         not null
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
    ID        NUMBER        not null
        constraint "Material_pk"
            primary key,
    NAME      VARCHAR2(100) not null,
    PRICE     NUMBER        not null,
    INVENTORY NUMBER        not null,
    REMAINING NUMBER        not null
)
/

create trigger MATERIAL_ID_TRIGGER
    before insert
    on "Material"
    for each row
begin
    select MATERIAL_ID_SEQ.nextval into :new.ID from dual;
end MATERIAL_ID_TRIGGER;
/

create table "Product"
(
    ID               NUMBER        not null
        constraint "Product_pk"
            primary key,
    NAME             VARCHAR2(100) not null,
    PRICE            NUMBER        not null,
    INVENTORY        NUMBER        not null,
    REMAINING        NUMBER        not null,
    MATERIAL_ID      NUMBER        not null
        constraint "Product_Material_ID_fk"
            references "Material",
    MATERIAL_CONSUME NUMBER        not null
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

create table "Company"
(
    ID   NUMBER        not null
        constraint "Company_pk"
            primary key,
    NAME VARCHAR2(100) not null,
    TEL  VARCHAR2(15)  not null
)
/

create trigger COMPANY_ID_TRIGGER
    before insert
    on "Company"
    for each row
begin
    select COMPANY_ID_SEQ.nextval into :new.ID from dual;
end COMPANY_ID_TRIGGER;
/

create table "SalesOrder"
(
    ID                NUMBER        not null
        constraint "SalesOrder_pk"
            primary key,
    PRODUCT_ID        NUMBER
        constraint "SalesOrder_Product_ID_fk"
            references "Product",
    SALES_EMPLOYEE_ID NUMBER        not null
        constraint "SalesOrder_Employee_ID_fk"
            references "Employee",
    COMPANY_ID        NUMBER        not null
        constraint "SalesOrder_Company_ID_fk"
            references "Company",
    REPO_EMPLOYEE_ID  NUMBER
        constraint "SalesOrder_Employee_ID_fk2"
            references "Employee",
    QUANTITY          NUMBER        not null,
    CONTRACT          BLOB          not null,
    APPLY_DATE        DATE          not null,
    STATUS            VARCHAR2(20)  not null,
    PRODUCT_NAME      VARCHAR2(100) not null
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
    ID                     NUMBER        not null
        constraint "PurchasingOrder_pk"
            primary key,
    MATERIAL_ID            NUMBER
        constraint "PurchasingOrder_Material_ID_fk"
            references "Material",
    PURCHASING_EMPLOYEE_ID NUMBER        not null
        constraint "PurchasingOrder_Employee_ID_fk"
            references "Employee",
    COMPANY_ID             NUMBER        not null
        constraint "PurchasingOrder_Company_ID_fk"
            references "Company",
    REPO_EMPLOYEE_ID       NUMBER
        constraint "PurchasingOrder_Employee_ID_fk2"
            references "Employee",
    QUANTITY               NUMBER        not null,
    CONTRACT               BLOB          not null,
    APPLY_DATE             DATE          not null,
    STATUS                 VARCHAR2(20)  not null,
    MATERIAL_NAME          VARCHAR2(100) not null
)
/


