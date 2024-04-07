# BasketSplitter

<p align="center">
  <img src="https://th.bing.com/th/id/OIG3.eB50HPX.Ym2fwddOQ9Vs?pid=ImgGn" width="500" height="500">
</p>

# What Is BasketSplitter?
Say goodbye to receiving multiple small packages for individual items! BasketSplitter is an **intelligent delivery management** library designed to **minimize the number of deliveries** while ensuring that your purchases reach your doorstep swiftly and efficiently. Whether you’re a seasoned bargain hunter or a time-strapped executive, this algorithm ensures that your items are bundled together in the most optimal way.

# Using BasketSplitter: A Step-by-Step Guide

<h3>Warning</h3>

- Make sure you have **Java 17** or higher version to avoid "Major-Minor Version" issue.

<h3>Method 1: Building from Source</h3>

<h4>Clone the Repository:</h4>

- First, navigate to the BasketSplitter repository on GitHub.
- Click the **<> Code** button to copy the repository URL.
- Open your terminal or command prompt and run:

  ```console
  git clone <repository_url>
  ```

Replace **<repository_url>** with the actual URL you copied.

<h4>Build the Project:</h4>

- Change into the cloned repository directory:
  ```console
  cd BasketSplitter/basket-splitter
  ```
- Run the following Maven command to build the project:

  ```console
  mvn clean package
  ```

- This will generate a **basket_splitter.jar** file in the target directory.
  
<h4>Usage:</h4>

- You now have the **basket_splitter.jar** file.
- Incorporate it into your projects by adding it to your classpath.
- Use the **BasketSplitter** objects in your code.

<br>
<h3>Method 2: Direct Download (Quick and Easy)</h3>

<h4>Download the JAR File:</h4>



- If you prefer not to build from source, you can directly **download** the **basket_splitter.jar** file from the repository.
- Look for the file in the repository’s **code section**.

<h4>Integration into Maven Project:</h4>

If you’re working with a Maven project:

- Open your **terminal** or **command prompt**.
- Run the following command, replacing **<path_to_jar>** with the actual path to the downloaded JAR file:

  ```console
  mvn install:install-file -Dfile=<path_to_jar> -DgroupId=com.ocado.basket -DartifactId=basket_splitter -Dversion=1.0.0 -Dpackaging=jar
  ```

- Add the following dependency to your project’s **pom.xml**:

  ```XML
  <dependency>
    <groupId>com.ocado.basket</groupId>
    <artifactId>basket_splitter</artifactId>
    <version>1.0.0</version>
  </dependency>
  ```

<br>
<h4>Basic Java App:</h4>

If you’re working with a basic Java application:


- **Compile** your Java code along with the **basket_splitter.jar**:

  ```console
  javac -cp <path_to_jar> Your_App.java
  ```

- **Run** your application:

  ```console
  java -cp <path_to_jar> Your_App.java
  ```
<br>

And that’s it! You’re all set to use **BasketSplitter** in your projects. If you’d like to see an **example app**, it’s available in the **examples** directory.

# Set Cover Problem for Intelligent Delivery Distribution

<h3>Problem Context</h3>

In our project, we’ve developed an algorithm that **optimizes** the **delivery process** for online stores. The goal is to efficiently distribute products from a customer’s shopping basket while **minimizing the number of deliveries**. Imagine a scenario where a customer orders multiple items, and our system needs to decide how to pack and deliver those items in the most effective way.

<h3>The Set Cover Problem</h3>

The Set Cover Problem serves as the backbone of our intelligent delivery distribution algorithm. Here’s why we’ve chosen this problem:

- **Coverage Efficiency:** Just like covering all elements in a universe, our application aims to **cover all the products** in a customer’s order. Each product corresponds to an element, and the sets represent products that can be delivered via the same method.
- **Minimizing Deliveries:** By selecting the **smallest sub-collection** of sets (i.e., delivery options), we ensure that the customer receives all their items in as few deliveries as possible. This directly aligns with our project’s objective of optimizing the delivery process. 

<h3>Problem Statement</h3>

**Given:**

- A **universe** of elements (often denoted as $\{1, 2, \ldots, n\}$).
- A collection of **subsets** (sets) whose union covers the entire universe.
<br>
The task is to identify the smallest sub-collection of sets whose union equals the universe. In other words, we want to find the most efficient way to cover all elements while minimizing the number of selected sets.

<h4>Example:</h4>

Let’s illustrate the problem with a simple example:

**Universe:**
<br>

  ```math
  U = \{1, 2, 3, 4, 5, 6\}
  ```
<br>

**Collection of sets:**
<br>

  ```math
  S = \{ \{1,2,3,6\}, \{1,2,4\}, \{3,4,5\} \}
  ```
<br>

Clearly, the union of all sets in S covers the entire universe U. However, we can achieve the same coverage using only **two sets**: 
<br>

  ```math
  \{ \{1,2,3,6\}, \{4,5\} \}
  ```
<br>
Therefore, the solution to the set cover problem in this case has a size of 2.

# Features
<h3>1. Config File Loading</h3>

- The configuration file (in **JSON** format) should contain a list of **items** that can be **delivered**.
- Next to each item, the configuration file should list the available **delivery options**. These options define how the item can be delivered. For example:

   ```json
  {
    "Strawberries": [
       "Pick-up Point",
       "Courier"
     ],
    "Dining table": ["Courier"]
  }
   ```
  In this configuration:
  
  - **Strawberries** can be delivered either via a **Pick-up Point** or by **Courier**.
  - **The Dining table** is exclusively available for delivery through the **Courier** service.

<h3>2. Basket Optimization</h3>

- Load a shopping basket from a separate **JSON** file. The basket includes the **items selected** by the user. For example:

  ```json
  [
    "Strawberries",
    "Dining Table"
  ]
   ```
  
- BasketSplitter will intelligently **group items** from the basket to minimize the number of deliveries.

<h3>3. Output File Saving</h3>

- After optimization, BasketSplitter saves the **optimized basket** to an output **JSON** file. This file contains the grouped items and their delivery details. For example:

   ```json
  {
    "Courier": [
      "Strawberries",
      "Dinning table"
    ]
  }
   ```
  
<h3>4. Format</h3>

- **JSON Compatibility:** All input and output files (config, basket, and output) must be in JSON format, using pattern specified earlier.

<br>
<h2>Ready to optimize your shopping experience? Let’s make deliveries smarter 📦</h2>
