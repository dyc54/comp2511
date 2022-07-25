1. ***TODO:*** 把BuildEntity 单独放出来
2. Duration 写成了一个类 
    - DurabilityObserver -> 有duration 的 entity
    - DurabilitySubject -> Duration -> 在Player里
    - **NOTE:**: 还没想好 有这个想法。
3. EntityFactory新增了几个可以被用json文件创造的entity
    - 只有最基本的，其余都需要补充
4. **所有的 Random都需要有一个相对于回合或者地图固定的seed**
    - 比如说 随机生成蜘蛛 seed = time， RandomMovement ->  seed = Location.hashCode() 
    - 如果 hascode 导致了int 的 overflow， 就去location 里改一下，这个我没有测。 随便一个复杂乘除法就行，乘法的系数最好用小质数。
5. 其中的一个BOSS `assassin`从原来的雇佣兵继承来
    - 话说 这个 雇佣兵和刺客有没有可能是一个template pattern