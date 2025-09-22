
# 🚢 Ship-Fast – Maritime Shipping Service Management System

## 📌 Project Purpose
**Ship-Fast** is a comprehensive maritime shipping management system designed to **streamline and automate shipping operations**.  
It provides an integrated platform for shipping companies to:

- ✅ Track service requests and inquiries
- ✅ Generate invoices and manage currency conversions
- ✅ Securely store and manage shipping documentation
- ✅ Conduct online meetings
- ✅ Create real-time reports for staff and clients

The system improves operational efficiency, reduces manual errors, and delivers **real-time updates** for staff and clients.

---

## ⚙️ Setup Instructions

### 🔹 Backend Setup

1. **Clone the repository**
  ```bash
  git clone https://github.com/dilizz713/Maritime-Shipping-Service-Management-System-ShipFast.git
```

3. **Navigate to the backend module**
 ```bash
   cd Maritime-Shipping-Service-Management-System-ShipFast/backend
   ```

4. **Configure environment variables**
  - ✅ Edit application.properties with your local database and JWT settings:

**Database Configuration**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ShipFast?createDatabaseIfNotExist=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.hikari.maximum-pool-size=10
```

**JPA Configuration**
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
```

**JWT Authentication**
```properties
jwt.secretKey=your_secretkey
```

5. **Install dependencies**
- Use Maven to install all dependencies:
```bash
mvn clean install
```

6. **Run the backend**
```bash
mvn spring-boot:run
```

- Backend runs on:
👉 http://localhost:8080

### 🔹 FrontEnd Setup

1. **Navigate to the frontend directory:**
```bash
   cd Maritime-Shipping-Service-Management-System-ShipFast/FrontEnd
```

2. **Open index.html directly in your browser to launch the frontend.**

### 🔹 API Documentation
- **Ship-Fast** includes **Swagger API Documentation** for testing and exploring APIs.
    1. Start the backend server
    2. Open Swagger in your browser:
       👉 http://localhost:8080/swagger-ui/index.html
  
### 🚀 Features at a Glance

✅ Service Request Tracking
✅ Inquiry Management
✅ Automated Invoice Generation
✅ Currency Conversion
✅ Secure Document Storage
✅ Online Meetings Support
✅ Real-Time Reports


## 🛠️ Technology Stack

**Backend:**  
- Java  
- Spring Boot  
- Hibernate  
- MySQL  
- Spring Security  
- JWT  

**Frontend:**  
- HTML  
- CSS  
- Bootstrap  
- JavaScript  
- jQuery  

**APIs & Integrations:**  
- Swagger  
- Invoice Generator API  
- Exchange Rate API  
- Apache POI  
- Spring Mail  

**File & Cloud Handling:**  
- SheetJS  
- Cloudinary

## 🎥 YouTube Demo
[Watch the Ship-Fast Demo on YouTube](https://youtu.be/RKVpKVsF_Mc)

## 📸 Screenshots

### Website
<img width="1919" height="910" alt="Screenshot 2025-09-21 210323" src="https://github.com/user-attachments/assets/25ced182-87d3-4f2a-8534-c754185f98c2" />

### Customer Dashboard
<img width="1919" height="914" alt="Screenshot 2025-09-21 210346" src="https://github.com/user-attachments/assets/7f86caf7-c0cc-40f7-9372-338a00cff310" />

### Signin/Signup
<img width="1916" height="913" alt="Screenshot 2025-09-22 205914" src="https://github.com/user-attachments/assets/c7011fc9-2877-4784-a4c3-76191abad85a" />

### Admin Dashboard
<img width="1919" height="912" alt="Screenshot 2025-09-21 210415" src="https://github.com/user-attachments/assets/fd68e64a-e901-4416-9c82-6eb1afe820da" />

### Customer Management
<img width="1919" height="911" alt="Screenshot 2025-09-22 205937" src="https://github.com/user-attachments/assets/11777655-02be-49e8-9a92-69200f99736c" />

### Service Management
<img width="1919" height="908" alt="Screenshot 2025-09-22 205953" src="https://github.com/user-attachments/assets/2dc92ad2-fd12-4ecc-858a-8c7634c0d5a4" />

### Inventory and Vendor Management
<img width="1917" height="912" alt="Screenshot 2025-09-22 210008" src="https://github.com/user-attachments/assets/81d44a10-de79-47a0-b043-6f36d4c1bdfc" />
<img width="1919" height="915" alt="Screenshot 2025-09-22 210023" src="https://github.com/user-attachments/assets/e21dcaf3-7915-452f-9019-8be9e32b6e56" />
<img width="1919" height="917" alt="Screenshot 2025-09-22 210037" src="https://github.com/user-attachments/assets/3f0ba402-227c-490d-83f0-59569d8d227d" />
<img width="1919" height="913" alt="Screenshot 2025-09-22 210050" src="https://github.com/user-attachments/assets/1032cc19-258d-4d12-a766-3e51b260d278" />
<img width="1919" height="911" alt="Screenshot 2025-09-22 210100" src="https://github.com/user-attachments/assets/017c6683-57ff-46e5-9e88-077b838eb554" />
<img width="1919" height="908" alt="Screenshot 2025-09-22 210109" src="https://github.com/user-attachments/assets/4e2ee09e-c6db-49c1-8f91-5455d5024a14" />
<img width="1919" height="909" alt="Screenshot 2025-09-22 210141" src="https://github.com/user-attachments/assets/99db0344-5bd7-4101-bf9d-70f2eb6922db" />
<img width="1919" height="915" alt="Screenshot 2025-09-22 210205" src="https://github.com/user-attachments/assets/dcc6d39a-67f4-4c88-83b1-43bc8611d33a" />

### Employee Management
<img width="1919" height="905" alt="Screenshot 2025-09-22 210227" src="https://github.com/user-attachments/assets/0e4ebd2b-fa2a-4256-8e8a-edc54e1b3361" />

### Vehicle Management
<img width="1917" height="917" alt="Screenshot 2025-09-22 210245" src="https://github.com/user-attachments/assets/a4ecadd1-839c-4b39-83ef-316d32a2ff47" />

### Job Management
<img width="1919" height="908" alt="Screenshot 2025-09-22 210303" src="https://github.com/user-attachments/assets/24e3addf-2df3-4da7-abd1-0f980e41ff1c" />
<img width="1919" height="911" alt="Screenshot 2025-09-22 210320" src="https://github.com/user-attachments/assets/cdf9f582-bcc7-4ecb-b811-1874215df306" />
<img width="1919" height="919" alt="Screenshot 2025-09-22 210336" src="https://github.com/user-attachments/assets/31c1be59-ccb2-43bf-b7b3-ccb3f47c89d5" />
<img width="1919" height="915" alt="Screenshot 2025-09-22 210359" src="https://github.com/user-attachments/assets/0e1f30c7-ee78-414c-9b53-fd3756baec59" />

### Meetings
<img width="1919" height="914" alt="Screenshot 2025-09-22 195344" src="https://github.com/user-attachments/assets/8a6566d0-90b3-4994-880d-34f90b4002ae" />
<img width="1919" height="912" alt="Screenshot 2025-09-22 195400" src="https://github.com/user-attachments/assets/c6861e91-4034-482f-8eb0-978376f176e1" />

### Reports
<img width="1919" height="911" alt="Screenshot 2025-09-21 210606" src="https://github.com/user-attachments/assets/11fb340a-2982-4ae7-9ef2-b5c8e45865b6" />

### PDF Files , Invoice and Emails
<img width="1919" height="881" alt="Screenshot 2025-09-21 210949" src="https://github.com/user-attachments/assets/0a160569-a06e-4f86-ae47-ef488c57cd5c" />
<img width="1919" height="878" alt="Screenshot 2025-09-21 211022" src="https://github.com/user-attachments/assets/8ae1e3aa-60c1-44cf-a272-4d03aefeaf8e" />
<img width="690" height="604" alt="Screenshot 2025-09-21 214135" src="https://github.com/user-attachments/assets/376f3aff-4b2b-41d0-a813-4ca1406f40cb" />
<img width="553" height="513" alt="Screenshot 2025-09-21 214220" src="https://github.com/user-attachments/assets/14c98939-1d36-4c24-8d79-636e2780b7a2" />


















