# PropertyManager
这是一个资源管理程序
# 使用说明
    这是一个通用的java项目，可以直接导入编译器运行，项目编码为utf-8，项目是一个简单的窗口程序，切换页面按钮在菜单栏。

    项目一共有5个页面，分别为：
    欢迎页面：
        显示所有资源，并显示欢迎使用系统的欢迎语。
    添加页面：
        显示所有资源，和添加资源的表单，用户可根据需要填写表单添加资源。
    分配页面：
        显示储存在仓库的待分配资源，用户可以选中资源并通过输入框输入分配位置，点击按钮完成分配。
    报修页面：
        显示部分资源（除去维修中的资源），用户可以选中资源点击按钮完成报修流程。
    维修页面：
        显示报修后的维修中资源，用户可选中资源点击按钮将已维修好的资源存入仓库。
# 设计思路
    程序需求指出是一个交互式系统，所以在软件体系结构模式上我采用MVC设计模式。
    MVC设计模式将代码分为了三层，我的命名分别为：
    entry包对应M（数据模型）
    view包对应V（视图）
    control包对应C（控制器）
    为了程序的健壮性，我又加了自定义异常，异常从底层抛出，高层捕获并解决，并名命为：
    exception包（自定义异常）
    
    我从数据层开始实现，实现数据层时考虑到程序的可扩展性，我将所有数据抽象为Property类型，
    桌子和椅子都继承Property类型，并且如要在后期增加数据类型，也几乎不需要对程序进行多少修改，
    因为程序与具体的数据几乎没有耦合（C与具体数据没有耦合，V与具体数据有少量耦合），耦合几乎都
    建立在抽象的Property类型上。并且我对具体数据类型如：桌子，椅子等的实现都覆盖了equals与hashcode方法，
    覆盖时遵循价值原则，我认为价值相同即与价值有关的属性（批号，类型等等）完全一致时返回true，否则返回false，
    这方便了我后面对资源价格的记录，和一些细节的实现。
    
    然后我开始实现控制层，实现时考虑到需求变更或者因为性能原因需要更换实现，控制层采用PropertyManager接口，
    降低了程序耦合，使控制层易更改和替换，PropertyManager提供7个基本功能接口，分别为：
    void propertyChange(Property property) throws PropertyOSException：
        资源位置改变回调，用于监听资源状态，资源位置一旦移动，资源管理对象就能立即做出反应。
    void add(Property property)：
        资源添加接口，将资源添加进资源管理对象管理，程序最基本的功能。
    List<Property> getAllProperty()：
        资源查询接口，查询所有资源，程序最基本的功能。
    List<Property> getPropertyByLocal(String local) throws PropertyOSException：
        资源查询接口，查询在指定位置上的资源。
    List<Property> getPropertyNotLocal(String local) throws PropertyOSException;
        资源查询接口，查询不在指定位置上的资源。
    void registerPrice(Map<Property,Float> priceMap) throws PropertyOSException：
        资源价格注册接口，将每种价格注册到资源管理对象中。
    float getPrice(Property property)：
        资源价格查询接口，根据之前注册的价格映射进行查询资源价格。
        
    最后实现的视图层，视图层是最容易需要修改的，所以最后实现，其他两层不得对视图层引用，当视图层拿掉时，不对其他两层有任何影响，
    其他两层可以直接复用，视图层我采用的是java的Swing实现的，具体实现方式这里不做细谈。
    